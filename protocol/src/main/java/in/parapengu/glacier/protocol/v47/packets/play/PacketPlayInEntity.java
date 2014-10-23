package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInEntity extends IncomingPacket {

    protected int entityId;

    public PacketPlayInEntity() {
        this(0x14);
    }

    public PacketPlayInEntity(int id) {
        super(id, ProtocolState.PLAY);
    }

    public int getEntityId() {
        return entityId;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.entityId = input.readVarInt();
    }

}
