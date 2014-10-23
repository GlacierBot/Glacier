package in.parapengu.glacier.handler.network.protocol.packet;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public abstract class OutgoingPacket extends Packet {

    protected OutgoingPacket(int id, ProtocolState state) {
        super(id, state);
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        throw new IOException("Can't read an outgoing packet!");
    }

}
