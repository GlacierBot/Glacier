package in.parapengu.glacier.handler.network.protocol.exception;

import in.parapengu.glacier.handler.network.protocol.ProtocolBuilder;

public class PacketNotFoundException extends Exception {

    private static final long serialVersionUID = -2753114704710077722L;

    private int id;
    private ProtocolBuilder protocol;

    public PacketNotFoundException(int id, ProtocolBuilder protocol) {
        super("Could not find Packet #" + id + " in " + protocol.getClass().getSimpleName());
        this.id = id;
        this.protocol = protocol;
    }

    public int getId() {
        return id;
    }

    public ProtocolBuilder getProtocol() {
        return protocol;
    }

}
