package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.exception.PacketException;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInJoinGame extends IncomingPacket {

    private int entityId;
    private int gamemode;
    private byte dimension;
    private int difficulty;
    private int maxPlayers;
    private String level;
    private boolean reducedDebug;

    public PacketPlayInJoinGame() {
        super(0x01, ProtocolState.PLAY);
    }

    public int getEntityId() {
        return entityId;
    }

    public int getGamemode() {
        return gamemode;
    }

    public byte getDimension() {
        return dimension;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getLevel() {
        return level;
    }

    public boolean isReducedDebug() {
        return reducedDebug;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.entityId = input.readInt();
        this.gamemode = input.readUnsignedByte();
        this.dimension = input.readByte();
        this.difficulty = input.readUnsignedByte();
        this.maxPlayers = input.readUnsignedByte();
        this.level = input.readString();
        this.reducedDebug = input.readBoolean();

        if (dimension < -1 || dimension > 1) {
            throw new PacketException("Illegal dimension '" + dimension + "'");
        }
    }

}
