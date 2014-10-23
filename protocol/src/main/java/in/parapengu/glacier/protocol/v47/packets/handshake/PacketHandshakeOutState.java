package in.parapengu.glacier.protocol.v47.packets.handshake;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.OutgoingPacket;
import in.parapengu.glacier.handler.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketHandshakeOutState extends OutgoingPacket {

    private int version;
    private String address;
    private int port;
    private int nextState;

    public PacketHandshakeOutState() {
        super(0x00, ProtocolState.UNKNOWN);
    }

    public PacketHandshakeOutState(int version, String address, int port, int nextState) {
        this();
        this.version = version;
        this.address = address;
        this.port = port;
        this.nextState = nextState;
    }

    public int getVersion() {
        return version;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getNextState() {
        return nextState;
    }

    @Override
    public void write(PacketOutputStream output) throws IOException {
        output.writeVarInt(version);
        output.writeString(address);
        output.writeUnsignedShort(port);
        output.writeVarInt(nextState);
    }

}
