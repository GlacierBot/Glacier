package in.parapengu.glacier.protocol.v47;

import in.parapengu.glacier.handler.network.connection.Connection;
import in.parapengu.glacier.handler.network.protocol.Protocol;
import in.parapengu.glacier.handler.network.protocol.ProtocolBuilder;
import in.parapengu.glacier.handler.network.protocol.exception.IllegalPacketException;
import in.parapengu.glacier.protocol.v47.packets.handshake.PacketHandshakeOutState;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInChatMessage;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInEntity;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInEntityEquipment;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInJoinGame;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInKeepAlive;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInSpawnPosition;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInTimeUpdate;
import in.parapengu.glacier.protocol.v47.packets.play.PacketPlayInUpdateHealth;

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
        register(PacketPlayInEntityEquipment.class);
        register(PacketPlayInSpawnPosition.class);
        register(PacketPlayInUpdateHealth.class);
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
        // Entity Relative Move (0x15)
        // Entity Look (0x16)
        // Entity Look and Relative Move (0x17)
        // Entity Teleport (0x18)
        // Entity Head Look (0x19)
        // Entity Status (0x1A) - Uses Int as Entity ID
        // Attach Entity (0x1B)
        // Entity Metadata (0x1C)
        // Entity Effect (0x1D)
        // Remove Entity Effect (0x1E)
        // Set Experience (0x1F)
        // Entity Properties (0x20)
        // Chunk Data (0x21)
        // Multi Block Change (0x22)
        // Block Change (0x23)
        // Block Action (0x24)
        // Block Break Animation (0x25)
        // Map Chunk Bulk (0x26)
        // Explosion (0x27)
        // Effect (0x28)
        // Sound Effect (0x29)
        // Particle (0x2A)
        // Change Game State (0x2B)
        // Spawn Global Entity (0x2C)
        // Open Window (0x2D)
        // Close Window (0x2E)
        // Set Slot (0x2F)
        // Window Items (0x30)
        // Window Property (0x31)
        // Confirm Transaction (0x32)
        // Update Sign (0x33)
        // Maps (0x34)
        // Update Block Entity (0x35)
        // Sign Editor Open (0x36)
        // Statistics (0x37)
        // Player List Item (0x38)
        // Player Abilities (0x39)
        // Tab-Complete (0x3A)
        // Scoreboard Objective (0x3B)
        // Update Score (0x3C)
        // Display Scoreboard (0x3D)
        // Teams (0x3E)
        // Plugin Message (0x3F)
        // Disconnect (0x40)
        // Server Difficulty (0x41)
        // Combat Event (0x42)
        // Camera (0x43)
        // World Border (0x44)
        // Title (0x45)
        // Set Compression (0x46)
        // Player List Header/Footer (0x47)
        // Resource Pack Send (0x48)
        // Update Entity NBT (0x49)
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
