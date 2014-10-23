package in.parapengu.glacier.handler.network.stream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PacketOutputStream extends DataOutputStream {

    public PacketOutputStream(OutputStream out) {
        super(out);
    }

    public void writeString(String string) throws IOException {
        writeVarInt(string.length());
        write(string.getBytes("UTF-8"));
    }

    public void writeStringArray(String[] array) throws IOException {
        writeVarInt(array.length);
        for (String string : array) {
            writeString(string);
        }
    }

    public void writeStringList(List<String> list) throws IOException {
        writeVarInt(list.size());
        for (String string : list) {
            writeString(string);
        }
    }

    public void writeVarInt(int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                writeByte((byte) paramInt);
                return;
            }

            writeByte((byte) (paramInt & 0x7F | 0x80));
            paramInt >>>= 7;
        }
    }

    public void writeVarInt64(long varInt) throws IOException {
        int length = 10;
        for (int i = 9; i >= 0; i--)
            if (((varInt >> (i * 7)) & (i != 9 ? 0x7F : 0x01)) == 0)
                length--;
        for (int i = 0; i < length; i++)
            writeByte((int) ((i == length - 1 ? 0x00 : 0x80) | ((varInt >> (i * 7)) & (i != 9 ? 0x7F : 0x01))));
    }

    public void writeBytes(byte[] data) throws IOException {
        writeVarInt(data.length);
        write(data);
    }

    public void writeUnsignedByte(int data) throws IOException {
        write(data);
    }

    public void writeUnsignedShort(int data) throws IOException {
        writeShort(data & 0xFFFF);
    }

    public void writeFixedPointByte(double value) throws IOException {
        int i = (int) value * 32;
        writeByte(i);
    }

    public void writeFixedPointInt(double value) throws IOException {
        int i = (int) value * 32;
        writeInt(i);
    }

    public void writeRotation(float value) throws IOException {
        int i = (int) (value * 256D) / 360;
        writeByte(i);
    }

}
