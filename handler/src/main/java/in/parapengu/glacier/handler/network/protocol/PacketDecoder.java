package in.parapengu.glacier.handler.network.protocol;

import in.parapengu.glacier.handler.network.DecoderState;
import in.parapengu.glacier.handler.network.connection.Connection;
import in.parapengu.glacier.handler.network.protocol.packet.UnreadPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class PacketDecoder extends ReplayingDecoder<DecoderState> {

    private Connection connection;

    public PacketDecoder(Connection connection) {
        state(DecoderState.LENGTH);
        this.connection = connection;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        UnreadPacket packet = connection.readUnreadPacket(null, this, in);
        if (packet == null) {
            packet = new UnreadPacket();
        }

        out.add(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public DecoderState state(DecoderState newState) {
        return super.state(newState);
    }

}
