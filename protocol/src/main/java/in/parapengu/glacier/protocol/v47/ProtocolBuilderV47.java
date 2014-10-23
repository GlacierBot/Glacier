package in.parapengu.glacier.protocol.v47;

import in.parapengu.glacier.handler.network.connection.Connection;
import in.parapengu.glacier.handler.network.protocol.Protocol;
import in.parapengu.glacier.handler.network.protocol.ProtocolBuilder;
import in.parapengu.glacier.handler.network.protocol.exception.IllegalPacketException;
import in.parapengu.glacier.protocol.v47.packets.handshake.PacketHandshakeOutState;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInChatMessage;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInEntity;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInJoinGame;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInKeepAlive;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInTimeUpdate;

public class ProtocolBuilderV47 extends ProtocolBuilder<ProtocolV47> {

    public ProtocolBuilderV47() throws IllegalPacketException {
        super(ProtocolV47.class);

        // Handshake Packets
        register(PacketHandshakeOutState.class);

        // Play Packets (in)
        register(PacketPlayInKeepAlive.class);
        register(PacketPlayInJoinGame.class);
        register(PacketPlayInChatMessage.class);
        register(PacketPlayInTimeUpdate.class);
        // Entity Equipment (0x04)
        // Spawn Position (0x05)
        // Update Health (0x06)
        // Respawn (0x07)
        // Player Position and Look (0x08)
        // Held Item Change (0x09)
        // Use Bed (0x0A)
        // Animation (0x0B)
        // Spawn Player (0x0C)
        // Collect Item (0x0D)
        // Spawn Object (0x0E)
        // Spawn Mob (0x0F)
        // Spawn Painting (0x10)
        // Spawn Experience Orb (0x11)
        // Entity Velocity (0x12)
        // Destroy Entities (0x13)
        register(PacketPlayInEntity.class);
    }

    @Override
    public ProtocolV47 create() {
        return new ProtocolV47();
    }

    @Override
    public ProtocolV47 create(Connection connection) {
        return new ProtocolV47(connection);
    }

}
