package osrs.skills.fletching.aio.enums;

public enum TippedBolt {

    OPAL_BOLT(11, "Opal bolts", "Bronze bolts", "Opal bolt tips"),
    JADE_BOLT(26, "Jade bolts", "Blurite bolts", "Jade bolt tips"),
    PEARL_BOLT(41, "Pearl bolts", "Iron bolts", "Pearl bolt tips"),
    RED_TOPAZ_BOLT(48, "Red topaz bolts", "Steel bolts", "Topaz bolt tips"),
    BARBED_BOLT(51, "Barbed bolts", "Bronze bolts", "Barb bolttips"),
    SAPPHIRE_BOLT(56, "Sapphire bolts", "Mithril bolts", "Sapphire bolt tips"),
    EMERALD_BOLT(58, "Emerald bolts", "Mithril bolts", "Emerald bolt tips"),
    RUBY_BOLT(63, "Ruby bolts", "Adamant bolts", "Ruby bolt tips"),
    DIAMOND_BOLT(65, "Diamond bolts", "Adamant bolts", "Diamond bolt tips"),
    DRAGONSTONE_BOLT(71, "Dragonstone bolts", "Rune bolts", "Dragonstone bolt tips"),
    ONYX_BOLT(73, "Onyx bolts", "Rune bolts", "Onyx bolt tips"),
    AMETHYST_BOLT(76, "Amethyst broad bolts", "Broad bolts", "Amethyst bolt tips"),
    DRAGON_OPAL_BOLT(84, "Opal dragon bolts", "Dragon bolts", "Opal bolt tips"),
    DRAGON_JADE_BOLT(84, "Jade dragon bolts", "Dragon bolts", "Jade bolt tips"),
    DRAGON_PEARL_BOLT(84, "Pearl dragon bolts", "Dragon bolts", "Pearl bolt tips"),
    DRAGON_RED_TOPAZ_BOLT(84, "Topaz dragon bolts", "Dragon bolts", "Topaz bolt tips"),
    DRAGON_SAPPHIRE_BOLT(84, "Sapphire dragon bolts", "Dragon bolts", "Sapphire bolt tips"),
    DRAGON_EMERALD_BOLT(84, "Emerald dragon bolts", "Dragon bolts", "Emerald bolt tips"),
    DRAGON_RUBY_BOLT(84, "Ruby dragon bolts", "Dragon bolts", "Ruby bolt tips"),
    DRAGON_DIAMOND_BOLT(84, "Diamond dragon bolts", "Dragon bolts", "Diamond bolt tips"),
    DRAGON_DRAGONSTONE_BOLT(84, "Dragonstone dragon bolts", "Dragon bolts", "Dragonstone bolt tips"),
    DRAGON_ONYX_BOLT(84, "Onyx dragon bolts", "Dragon bolts", "Onyx bolt tips");

    private final int levelReq;
    private final String tippedBolt;
    private final String untippedBolt;
    private final String secondary;

    TippedBolt(final int id, final String tippedBolt, final String untippedBolt, final String secondary) {
        this.levelReq = id;
        this.tippedBolt = tippedBolt;
        this.untippedBolt = untippedBolt;
        this.secondary = secondary;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public String getTippedBolt() {
        return tippedBolt;
    }

    public String getUntippedBolt() {
        return untippedBolt;
    }

    public String getSecondary() {
        return secondary;
    }
}
