package in.parapengu.glacier.handler.network;

import in.parapengu.glacier.handler.network.connection.ConnectionHandler;
import in.parapengu.glacier.handler.network.protocol.PacketDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private NettyClient client;

    public NettyChannelInitializer(NettyClient client) {
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ConnectionHandler handler = new ConnectionHandler();
        PacketDecoder decoder = new PacketDecoder(handler);

        channel.pipeline().addLast(decoder, handler);
    }

}
