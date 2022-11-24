package osrs.skills.fletching.aio.enums;

public enum Bow {

    REGULAR_SHORTBOW(5, "Shortbow", "Shortbow (u)", "Logs", "Bow string"),
    REGULAR_LONGBOW(10, "Longbow", "Longbow (u)", "Logs", "Bow string"),
    OAK_SHORTBOW(20, "Oak shortbow", "Oak shortbow (u)", "Oak logs", "Bow string"),
    OAK_LONGBOW(25, "Oak longbow", "Oak longbow (u)", "Oak logs", "Bow string"),
    WILLOW_SHORTBOW(35, "Willow shortbow", "Willow shortbow (u)", "Willow logs", "Bow string"),
    WILLOW_LONGBOW(40, "Willow longbow", "Willow longbow (u)", "Willow logs", "Bow string"),
    MAPLE_SHORTBOW(50, "Maple shortbow", "Maple shortbow (u)", "Maple logs", "Bow string"),
    MAPLE_LONGBOW(55, "Maple longbow", "Maple longbow (u)", "Maple logs", "Bow string"),
    YEW_SHORTBOW(65, "Yew shortbow", "Yew shortbow (u)", "Yew logs", "Bow string"),
    YEW_LONGBOW(70, "Yew longbow", "Yew longbow (u)", "Yew logs", "Bow string"),
    MAGIC_SHORTBOW(80, "Magic shortbow", "Magic shortbow (u)", "Magic logs", "Bow string"),
    MAGIC_LONGBOW(85, "Magic longbow", "Magic longbow (u)", "Magic logs", "Bow string");

    private final int levelReq;
    private final String finishedBow;
    private final String bowUnf;
    private final String logs;
    private final String secondary;

    Bow(final int id, final String finishedBow, final String bowUnf, final String logs, final String secondary) {
        this.levelReq = id;
        this.finishedBow = finishedBow;
        this.bowUnf = bowUnf;
        this.logs = logs;
        this.secondary = secondary;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public String getFinishedBow() {
        return finishedBow;
    }

    public String getBowUnf() {
        return bowUnf;
    }

    public String getLogs() {
        return logs;
    }

    public String getSecondary() {
        return secondary;
    }
}
