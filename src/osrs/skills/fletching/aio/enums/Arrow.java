package osrs.skills.fletching.aio.enums;

public enum Arrow {

    HEADLESS_ARROW(1, "Headless arrow", "Arrow shaft", "Feather"),
    BRONZE_ARROW(1, "Bronze arrow", "Bronze arrowtips", "Headless arrow"),
    IRON_ARROW(15, "Iron arrow", "Iron arrowtips", "Headless arrow"),
    STEEL_ARROW(30, "Steel arrow", "Steel arrowtips", "Headless arrow"),
    MITHRIL_ARROW(45, "Mithril arrow", "Mithril arrowtips", "Headless arrow"),
    BROAD_ARROW(52, "Broad arrow", "Broad arrowheads", "Headless arrow"),
    ADAMANT_ARROW(60, "Adamant arrow", "Adamant arrowtips", "Headless arrow"),
    RUNE_ARROW(75, "Rune arrow", "Rune arrowtips", "Headless arrow"),
    AMETHYST_ARROW(82, "Amethyst arrow", "Dragon arrowtips", "Headless arrow"),
    DRAGON_ARROW(90, "Dragon arrow", "Amethyst arrowtips", "Headless arrow");

    private final int levelReq;
    private final String finishedArrow;
    private final String arrowUnf;
    private final String secondary;

    Arrow(final int id, final String finishedArrow, final String arrowUnf, final String secondary) {
        this.levelReq = id;
        this.finishedArrow = finishedArrow;
        this.arrowUnf = arrowUnf;
        this.secondary = secondary;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public String getFinishedArrow() {
        return finishedArrow;
    }

    public String getArrowUnf() {
        return arrowUnf;
    }

    public String getSecondary() {
        return secondary;
    }
}
