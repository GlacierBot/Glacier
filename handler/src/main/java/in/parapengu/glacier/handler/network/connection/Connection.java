package in.parapengu.glacier.handler.network.connection;

import in.parapengu.glacier.handler.network.protocol.PacketDecoder;
import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.Packet;
import in.parapengu.glacier.handler.network.protocol.packet.UnreadPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;

public interface Connection {

    public ProtocolState getState();

    public InetSocketAddress getAddress();

    public void sendPacket(Packet packet) throws IOException;

    public UnreadPacket readUnreadPacket(PacketInputStream input) throws IOException;

    public UnreadPacket readUnreadPacket(PacketInputStream input, PacketDecoder decoder, ByteBuf in) throws IOException;

    public Packet readPacket(UnreadPacket packet) throws IOException;

    public void close();

}
