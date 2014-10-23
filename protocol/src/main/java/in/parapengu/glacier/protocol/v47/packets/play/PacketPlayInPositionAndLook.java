package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.network.protocol.ProtocolState;
import in.parapengu.glacier.handler.network.protocol.packet.IncomingPacket;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInPositionAndLook extends IncomingPacket {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    private byte flag;
    private boolean relativeX;
    private boolean relativeY;
    private boolean relativeZ;
    private boolean relativeYaw;
    private boolean relativePitch;

    public PacketPlayInPositionAndLook() {
        super(0x08, ProtocolState.PLAY);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public byte getFlag() {
        return flag;
    }

    public boolean isRelativeX() {
        return relativeX;
    }

    public boolean isRelativeY() {
        return relativeY;
    }

    public boolean isRelativeZ() {
        return relativeZ;
    }

    public boolean isRelativeYaw() {
        return relativeYaw;
    }

    public boolean isRelativePitch() {
        return relativePitch;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        this.x = input.readDouble();
        this.y = input.readDouble();
        this.z = input.readDouble();
        this.yaw = input.readFloat();
        this.pitch = input.readFloat();

        this.flag = input.readByte();
        this.relativeX = (flag & 0x01) == 0x01;
        this.relativeY = (flag & 0x02) == 0x02;
        this.relativeZ = (flag & 0x04) == 0x04;
        this.relativeYaw = (flag & 0x08) == 0x08;
        this.relativePitch = (flag & 0x10) == 0x10;
    }

}
