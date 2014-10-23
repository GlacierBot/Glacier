package in.parapengu.glacier.protocol.v47.packets.play;

import in.parapengu.glacier.handler.bot.inventory.EntitySlot;
import in.parapengu.glacier.handler.bot.inventory.ItemStack;
import in.parapengu.glacier.handler.network.stream.PacketInputStream;

import java.io.IOException;

public class PacketPlayInEntityEquipment extends PacketPlayInEntity {

    private EntitySlot slot;
    private ItemStack item;

    public PacketPlayInEntityEquipment() {
        super(0x04);
    }

    public EntitySlot getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public void read(PacketInputStream input) throws IOException {
        super.read(input);
        this.slot = EntitySlot.valueOf(input.readShort());
        this.item = input.readItem();
    }

}
