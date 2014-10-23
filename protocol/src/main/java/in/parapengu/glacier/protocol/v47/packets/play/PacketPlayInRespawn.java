package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInRespawn extends IncomingPacket {

    private int dimension;
    private int difficulty;
    private int gamemode;
    private String level;

    public PacketPlayInRespawn() {
        super(0x07, ProtocolState.PLAY);
    }

    public int getDimension() {
        return dimension;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getGamemode() {
        return gamemode;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.dimension = input.readInt();
        this.difficulty = input.readUnsignedByte();
        this.gamemode = input.readUnsignedByte();
        this.level = input.readString();
    }

}
