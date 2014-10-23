package in.parapengu.glacier.handler.bot.inventory;

import com.evilco.mc.nbt.tag.ITag;

public class ItemStack {

    private short id;
    private byte amount;
    private short damage;
    private ITag nbt;

    public ItemStack(short id, byte amount, short damage, ITag nbt) {
        this.id = id;
        this.amount = amount;
        this.damage = damage;
        this.nbt = nbt;
    }

    public short getId() {
        return id;
    }

    public byte getAmount() {
        return amount;
    }

    public short getDamage() {
        return damage;
    }

    public ITag getNBT() {
        return nbt;
    }

}
