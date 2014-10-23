package in.parapengu.glacier.handler.network.stream;

import com.evilco.mc.nbt.stream.NbtInputStream;
import com.evilco.mc.nbt.tag.ITag;
import in.parapengu.glacier.handler.bot.inventory.ItemStack;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PacketInputStream extends DataInputStream {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public PacketInputStream(InputStream in) {
        super(in);
    }

    public String readString() throws IOException {
        int length = readVarInt();
        // Logging.getLogger().info("Reading a string with " + length + " characters");
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = (byte) read();
        }
        String string = new String(data, UTF8);
        // Logging.getLogger().info("Reading " + string);

        return string;
    }

    public String[] readStringArray() throws IOException {
        int size = readVarInt();

        String[] array = new String[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = readString();
        }

        return array;
    }

    public List<String> readStringList() throws IOException {
        int size = readVarInt();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(readString());
        }

        return list;
    }

    public int readVarInt() throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = read();
            if (k == -1)
                throw new IOException("End of stream");

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5)
                throw new IOException("VarInt too big");

            if ((k & 0x80) != 128)
                break;
        }

        return i;
    }

    public long readVarInt64() throws IOException {
        long varInt = 0;
        for (int i = 0; i < 10; i++) {
            byte b = readByte();
            varInt |= ((long) (b & (i != 9 ? 0x7F : 0x01))) << (i * 7);

            if (i == 9 && (((b & 0x80) == 0x80) || ((b & 0x7E) != 0)))
                throw new IOException("VarInt too big");
            if ((b & 0x80) != 0x80)
                break;
        }

        return varInt;
    }

    public byte[] readBytes() throws IOException {
        return readBytes(readVarInt());
    }

    public byte[] readBytes(int length) throws IOException {
        if (length < 0)
            throw new IOException("Invalid array length");
        byte[] data = new byte[length];
        readFully(data);
        return data;
    }

    public double readFixedPointByte() throws IOException {
        return (double) readByte() / 32;
    }

    public double readFixedPointInt() throws IOException {
        return (double) readInt() / 32;
    }

    public float readRotation() throws IOException {
        return (float) (readByte() / 360) * 256F;
    }

    public ItemStack readItem() throws IOException {
        short id = readShort();
        if (id == -1) {
            return null;
        }

        byte count = readByte();
        short damage = readShort();
        NbtInputStream nbt = new NbtInputStream(this);
        ITag tag = nbt.readTag();
        return new ItemStack(id, count, damage, tag);
    }

}
