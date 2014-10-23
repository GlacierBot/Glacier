package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInHeldItemChange extends IncomingPacket {

    private byte slot;

    public PacketPlayInHeldItemChange() {
        super(0x09, ProtocolState.PLAY);
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.slot = input.readByte();
    }

}
