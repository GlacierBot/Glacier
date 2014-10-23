package in.parapengu.glacier.handler.network;

import in.parapengu.glacier.handler.logging.Logging;
import in.parapengu.glacier.handler.network.connection.ConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NettyClient extends Thread {

    private InetSocketAddress address;
    private Channel channel;
    private ChannelFuture future;
    private EventLoopGroup worker;

    private NettyChannelInitializer initializer;
    private ConnectionHandler handler;

    public NettyClient(InetSocketAddress address) {
        this.address = address;
        this.initializer = new NettyChannelInitializer(this);
    }

    @Override
    public void run() {
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(initializer)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            Logging.getLogger().debug("Connecting to " + address.getHostName() + ":" + address.getPort() + "...");
            ChannelFuture future = b.connect(address).sync();
            channel = future.channel();
            future = channel.closeFuture();

            handler = channel.pipeline().get(ConnectionHandler.class);
        } catch (Exception ex) {
            Logging.getLogger().log(ex);
        }
    }

}
