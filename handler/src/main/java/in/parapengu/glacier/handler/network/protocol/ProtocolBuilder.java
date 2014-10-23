package in.parapengu.glacier.handler.network.protocol;

import in.parapengu.glacier.handler.network.connection.Connection;
import in.parapengu.glacier.handler.network.protocol.exception.IllegalPacketException;
import in.parapengu.glacier.handler.network.protocol.exception.PacketNotFoundException;
import in.parapengu.glacier.handler.network.protocol.packet.Packet;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class ProtocolBuilder<T extends Protocol> {

    private Class<T> cls;
    private Map<ProtocolState, Map<Integer, Class<? extends Packet>>> packets;

    public ProtocolBuilder(Class<T> cls) {
        this.cls = cls;
        this.packets = new HashMap<>();
        for (ProtocolState state : ProtocolState.values()) {
            packets.put(state, new HashMap<>());
        }
    }

    public Class<T> getType() {
        return cls;
    }

    public abstract T create();

    public abstract T create(Connection connection);

    public Map<ProtocolState, Map<Integer, Class<? extends Packet>>> getPackets() {
        return packets;
    }

    public Class<? extends Packet> find(ProtocolState state, int id) {
        return packets.get(state).get(id);
    }

    public Packet build(ProtocolState state, int id) throws PacketNotFoundException, IllegalPacketException {
        Class<? extends Packet> cls = find(state, id);
        if (cls == null) {
            throw new PacketNotFoundException(id, this);
        }

        try {
            Constructor<? extends Packet> constructor = cls.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception ex) {
            throw new IllegalPacketException(cls, (ex.getMessage() != null ? ex.getMessage() : "Failed to construct Packet") + " (" + ex.getClass().getSimpleName() + ")");
        }
    }

    protected <P extends Packet> void register(Class<P> packet) throws IllegalPacketException {
        try {
            Constructor<P> constructor = packet.getDeclaredConstructor();
            constructor.setAccessible(true);
            Packet result = constructor.newInstance();
            packets.get(result.getState()).put(result.getId(), packet);
        } catch (Exception ex) {
            throw new IllegalPacketException(packet, (ex.getMessage() != null ? ex.getMessage() : "Failed to construct Packet") + " (" + ex.getClass().getSimpleName() + ")");
        }
    }

}
