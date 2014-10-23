package in.parapengu.glacier.handler.bot.inventory;

public enum EntitySlot {

    HAND(0),
    BOOTS(1),
    LEGGINGS(2),
    CHESTPLATE(3),
    HELMET(4);

    private int id;

    EntitySlot(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static EntitySlot valueOf(int id) {
        for (EntitySlot state : values()) {
            if (state.getId() == id) {
                return state;
            }
        }

        return null;
    }

}
