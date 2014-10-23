package in.parapengu.glacier.handler.network.protocol.exception;

import in.parapengu.glacier.handler.network.protocol.packet.Packet;

public class IllegalPacketException extends Exception {

    private static final long serialVersionUID = -2753114704710077722L;

    private Class<? extends Packet> packet;
    private String reason;

    public IllegalPacketException(Class<? extends Packet> packet, String reason) {
        super("Illegal Packet (" + packet.getSimpleName() + "): " + reason);
        this.packet = packet;
        this.reason = reason;
    }

    public Class<? extends Packet> getPacket() {
        return packet;
    }

    public String getReason() {
        return reason;
    }

}
