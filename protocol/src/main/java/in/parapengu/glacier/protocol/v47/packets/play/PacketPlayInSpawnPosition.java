package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInSpawnPosition extends IncomingPacket {

    private int[] location;

    public PacketPlayInSpawnPosition() {
        super(0x05, ProtocolState.PLAY);
    }

    public int[] getLocation() {
        return location;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.location = input.readLocation();
    }

}
