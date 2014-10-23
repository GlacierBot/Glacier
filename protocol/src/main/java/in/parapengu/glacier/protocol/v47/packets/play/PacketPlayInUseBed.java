package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInUseBed extends PacketPlayInEntity {

    private int[] location;

    public PacketPlayInUseBed() {
        super(0x0A);
    }

    public int[] getLocation() {
        return location;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        super.read(input);
        this.location = input.readLocation();
    }

}
