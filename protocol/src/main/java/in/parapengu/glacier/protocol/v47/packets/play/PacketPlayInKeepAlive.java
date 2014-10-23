package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInKeepAlive extends IncomingPacket {

    private int aliveId;

    public PacketPlayInKeepAlive() {
        super(0x00, ProtocolState.PLAY);
    }

    public int getAliveId() {
        return aliveId;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.aliveId = input.readVarInt();
    }

}
