package in.parapengu.glacier.handler.network.protocol.packet;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.stream.PacketOutputStream;

import java.io.IOException;

public abstract class IncomingPacket extends Packet {

    protected IncomingPacket(int id, ProtocolState state) {
        super(id, state);
    }

    @Override
    public void write(PacketOutputStream output) throws IOException {
        throw new IOException("Can't write an outgoing packet!");
    }

}
