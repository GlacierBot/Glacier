package in.parapengu.glacier.handler.network.protocol.packet;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;
import in.parapengu.glacier.handler.network.stream.PacketOutputStream;

import java.io.IOException;

public abstract class Packet {

    private int id;
    private ProtocolState state;

    public Packet(int id, ProtocolState state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public ProtocolState getState() {
        return state;
    }

    public abstract void read(PacketInputStream input) throws IOException;

    public abstract void write(PacketOutputStream output) throws IOException;

}
