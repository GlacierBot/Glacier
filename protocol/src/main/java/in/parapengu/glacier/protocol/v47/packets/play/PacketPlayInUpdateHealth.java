package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInUpdateHealth extends IncomingPacket {

    private float health;
    private int food;
    private float saturation;

    public PacketPlayInUpdateHealth() {
        super(0x06, ProtocolState.PLAY);
    }

    public float getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public float getSaturation() {
        return saturation;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.health = input.readFloat();
        this.food = input.readVarInt();
        this.saturation = input.readFloat();
    }

}
