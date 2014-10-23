package in.parapengu.glacier.handler.network.protocol.packet;

import in.parapengu.glacier.handler.network.protocol.ProtocolBuilder;
import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.exception.IllegalPacketException;
import in.parapengu.glacier.handler.network.protocol.exception.PacketNotFoundException;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class UnreadPacket {

    private int length;
    private int id;
    private ProtocolState state;
    private byte[] bytes;

    public UnreadPacket() {
        this(-1, -1, ProtocolState.UNKNOWN, new byte[0]);
    }

    public UnreadPacket(int length, int id, ProtocolState state, byte[] bytes) {
        this.length = length;
        this.id = id;
        this.state = state;
        this.bytes = bytes;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public ProtocolState getState() {
        return state;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Packet read(ProtocolBuilder protocol) throws PacketNotFoundException, IllegalPacketException, IOException {
        Packet packet = protocol.build(state, id);
        ByteArrayInputStream array = new ByteArrayInputStream(bytes);
        GZIPInputStream gzip = new GZIPInputStream(array);
        PacketInputStream input = new PacketInputStream(gzip);

        packet.read(input);
        return packet;
    }

}
