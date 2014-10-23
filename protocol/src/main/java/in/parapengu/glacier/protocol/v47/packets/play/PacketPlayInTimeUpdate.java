package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInTimeUpdate extends IncomingPacket {

    private long age;
    private long time;

    public PacketPlayInTimeUpdate() {
        super(0x03, ProtocolState.PLAY);
    }

    public long getAge() {
        return age;
    }

    public long getTime() {
        return time;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.age = input.readLong();
        this.time = input.readLong();
    }

}
