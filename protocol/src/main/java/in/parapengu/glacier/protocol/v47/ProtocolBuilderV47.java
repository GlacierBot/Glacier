package in.parapengu.glacier.protocol.v47;

import in.parapengu.glacier.handler.network.connection.Connection;
import in.parapengu.glacier.handler.network.protocol.Protocol;
import in.parapengu.glacier.handler.network.protocol.ProtocolBuilder;
import in.parapengu.glacier.handler.network.protocol.exception.IllegalPacketException;
import in.parapengu.glacier.protocol.v47.packets.handshake.PacketHandshakeOutState;

public class ProtocolBuilderV47 extends ProtocolBuilder<ProtocolV47> {

    public ProtocolBuilderV47() throws IllegalPacketException {
        super(ProtocolV47.class);

        // Handshake Packets
        register(PacketHandshakeOutState.class);
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
