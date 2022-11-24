package osrs.skills.fletching.aio.enums;

public enum Dart {

    BRONZE_DART(10, "Bronze dart", "Bronze dart tip", "Feather"),
    IRON_DART(22, "Iron dart", "Iron dart tip", "Feather"),
    STEEL_DART(37, "Steel dart", "Steel dart tip", "Feather"),
    MITHRIL_DART(52, "Mithril dart", "Mithril dart tip", "Feather"),
    ADAMANT_DART(67, "Adamant dart", "Adamant dart tip", "Feather"),
    RUNE_DART(81, "Rune dart", "Rune dart tip", "Feather"),
    DRAGON_DART(95, "Dragon dart", "Dragon dart tip", "Feather");

    private final int levelReq;
    private final String finishedDart;
    private final String dartUnf;
    private final String secondary;

    Dart(final int id, final String finishedDart, final String dartUnf, final String secondary) {
        this.levelReq = id;
        this.finishedDart = finishedDart;
        this.dartUnf = dartUnf;
        this.secondary = secondary;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public String getFinishedDart() {
        return finishedDart;
    }

    public String getDartUnf() {
        return dartUnf;
    }

    public String getSecondary() {
        return secondary;
    }
}
