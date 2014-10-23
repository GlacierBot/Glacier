package in.parapengu.glacier.handler.network.connection;

import in.parapengu.glacier.handler.logging.Logger;
import in.parapengu.glacier.handler.network.DecoderState;
import in.parapengu.glacier.handler.network.NettyClient;
import in.parapengu.glacier.handler.network.protocol.PacketDecoder;
import in.parapengu.glacier.handler.network.protocol.ProtocolBuilder;
import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.Packet;
import in.parapengu.glacier.handler.network.protocol.packet.UnreadPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;
import in.parapengu.glacier.handler.network.stream.PacketOutputStream;
import in.parapengu.glacier.handler.utils.OtherUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler extends ChannelHandlerAdapter implements Connection {

    private NettyClient client;
    private InetSocketAddress address;

    private ProtocolBuilder builder;
    private ProtocolState state = ProtocolState.UNKNOWN;

    private Logger logger;
    private ChannelHandlerContext context;

    @Override
    public void handlerAdded(ChannelHandlerContext context) throws Exception {
        this.context = context;
        logger.debug("Added Handler (" + builder.getClass().getSimpleName() + "): " + context);
    }

    @Override
    public ProtocolState getState() {
        return state;
    }

    @Override
    public InetSocketAddress getAddress() {
        return address;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) {
            return;
        }

        /*
        UnreadPacket unread = (UnreadPacket) msg;
        if (unread.getLength() == -1) {
            return;
        }

        if (protocol == null) {
            protocol = global.create(this);
            context = ctx;

            SocketAddress socket = ctx.channel().remoteAddress();
            address = socket instanceof InetSocketAddress ? (InetSocketAddress) socket : null;
        }

        Packet packet = readPacket(unread);

        logger.debug("Read " + packet);
        protocol.handle(packet, ctx);
        */
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public synchronized void sendPacket(Packet packet) {
        new Thread(() -> sendUnthreadedPacket(packet)).start();
    }

    public synchronized void sendUnthreadedPacket(Packet packet) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PacketOutputStream data = new PacketOutputStream(stream);

            data.writeVarInt(packet.getId()); // write the packet id
            // display(stream, "Wrote packet id (" + packet.getId() + "): {LIST}", false);
            packet.write(data); // write the packet data
            // display(stream, "Wrote packet contents: {LIST}", false);
            sendPacket(stream);
            logger.debug("Wrote " + packet);
        } catch (Exception ex) {
            logger.log(ex);
        }
    }

    public synchronized void sendPacket(ByteArrayOutputStream dataBuf) throws IOException {
        ByteArrayOutputStream sendBuf = new ByteArrayOutputStream();
        PacketOutputStream send = new PacketOutputStream(sendBuf); // create a new final byte array

        send.writeVarInt(dataBuf.toByteArray().length); // write the length of the buffer
        // display(sendBuf, "Wrote length of buffer (" + dataBuf.toByteArray().length + "): {LIST}", false);
        send.write(dataBuf.toByteArray()); // write the array of bytes to the final byte array
        // display(sendBuf, "Wrote the bytes (" + dataBuf.toByteArray().length + "): {LIST}", false);

        send.close();
        List<Byte> list = OtherUtils.asList(sendBuf.toByteArray());
        logger.debug("Sending " + list.size() + " bytes: " + list);
        ByteBuf buffer = context.alloc().buffer(sendBuf.toByteArray().length);
        buffer.writeBytes(sendBuf.toByteArray());
        context.writeAndFlush(buffer); // write the final array to the data output stream then flush the output and send it on it's way
    }

    public UnreadPacket readUnreadPacket(PacketInputStream input) throws IOException {
        return readUnreadPacket(input, null, null);
    }

    public UnreadPacket readUnreadPacket(PacketInputStream input, PacketDecoder decoder, ByteBuf in) throws IOException {
        try {
            if (in != null) {
                in.markReaderIndex();
            }

            if (in != null) {
                DataInputStream stream = new DataInputStream(new ByteBufInputStream(in));
                input = new PacketInputStream(stream);
            }
            int length = input.readVarInt();

            if (in != null) {
                if (in.readableBytes() < length) {
                    state(decoder, DecoderState.COLLECTING);
                    in.resetReaderIndex();
                    return null;
                }
            }

            int id = input.readVarInt();
            byte[] bytes = input.readBytes(length);
            List<Byte> list = new ArrayList<>();
            list.add((byte) length);
            list.addAll(OtherUtils.asList(bytes));
            logger.debug("Read " + list.size() + " bytes: " + list);
            state(decoder, DecoderState.COLLECTED);
            return new UnreadPacket(length, id, state, bytes);
        } catch (EOFException ex) {
            logger.log(ex);
            return null;
        } catch (IOException ex) {
            logger.log(ex);
            return null;
        }
    }

    public Packet readPacket(UnreadPacket unread) throws IOException {
        Packet packet;
        try {
            packet = builder.build(unread.getState(), unread.getId());
        } catch (Exception ex) {
            logger.log(ex);
            return null;
        }

        return packet;
    }

    private DecoderState state(PacketDecoder decoder, DecoderState newState) {
        if (decoder == null) {
            return null;
        }

        return decoder.state(newState);
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    @Override
    public void close() {
        context.close();
    }

}
