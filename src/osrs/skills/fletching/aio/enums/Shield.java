package osrs.skills.fletching.aio.enums;

public enum Shield {

    OAK_SHIELD(27, "Oak shield", "Oak logs"),
    WILLOW_SHIELD(42, "Willow shield", "Willow logs"),
    MAPLE_SHIELD(57, "Maple shield", "Maple logs"),
    YEW_SHIELD(72, "Yew shield", "Yew logs"),
    MAGIC_SHIELD(87, "Magic shield", "Magic logs"),
    ELDER_SHIELD(92, "Elder shield", "Elder logs");

    private final int levelReq;
    private final String finishedShield;
    private final String logs;

    Shield(final int id, final String finishedShield, final String logs) {
        this.levelReq = id;
        this.finishedShield = finishedShield;
        this.logs = logs;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public String getFinishedShield() {
        return finishedShield;
    }

    public String getLogs() {
        return logs;
    }
}
