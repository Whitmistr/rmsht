package osrs.skills.fletching.aio.enums;

public enum Bolt {

    BRONZE_BOLT(9, "Bronze bolt", "Bronze bolts (unf)", "Feather"),
    IRON_BOLT(39, "Iron bolt", "Bronze bolts (unf)", "Feather"),
    SILVER_BOLT(43, "Silver bolt", "Silver bolts (unf)", "Feather"),
    STEEL_BOLT(46, "Steel bolt", "Bronze bolts (unf)", "Feather"),
    MITHRIL_BOLT(54, "Mithril bolt", "Bronze bolts (unf)", "Feather"),
    BROAD_BOLT(55, "Broad bolt", "Unfinished broad bolts", "Feather"),
    ADAMANT_BOLT(61, "Adamant bolt", "Bronze bolts (unf)", "Feather"),
    RUNE_BOLT(69, "Rune bolt", "Bronze bolts (unf)", "Feather"),
    DRAGON_BOLT(84, "Dragon bolt", "Bronze bolts (unf)", "Feather");

    private final int levelReq;
    private final String finishedBolt;
    private final String boltUnf;
    private final String secondary;

    Bolt(final int id, final String finishedBolt, final String boltUnf, final String secondary) {
        this.levelReq = id;
        this.finishedBolt = finishedBolt;
        this.boltUnf = boltUnf;
        this.secondary = secondary;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public String getFinishedBolt() {
        return finishedBolt;
    }

    public String getBoltUnf() {
        return boltUnf;
    }

    public String getSecondary() {
        return secondary;
    }
}