package osrs.skills.herblore.aio.enums;

/**
 * @Author Ned
 */

public enum Tar {

    GUAM_TAR(0,"Guam tar","Swamp tar", 390, "Guam leaf", 26),
    MARRENTILL_TAR(1,"Marrentill tar","Swamp tar", 390, "Marrentill", 26),
    TARROMIN_TAR(2,"Tarromin tar","Swamp tar", 390, "Tarromin", 26),
    HARRALANDER_TAR(3,"Harralander tar","Swamp tar", 390, "Harralander", 26);

    private final String finalProduct;
    private final String tar;
    private final String herb;
    private final int id;
    private final int tarQt;
    private final int herbQt;
    private final Tar[] tars;

    Tar(final int id, final String finalProduct, final String tar, final int tarQt, final String herb, final int herbQt) {
        this.id = id;
        this.finalProduct = finalProduct;
        this.tar = tar;
        this.tarQt = tarQt;
        this.herb = herb;
        this.herbQt = herbQt;

        tars = null;
    }

    public String getFinalProduct() {
        return finalProduct;
    }

    public String getTar() {
        return tar;
    }

    public String getHerb() {
        return herb;
    }

    public int getId() {
        return id;
    }

    public int getTarQt() {
        return tarQt;
    }

    public int getHerbQt() {
        return herbQt;
    }

    public Tar[] getTars() {
        return tars;
    }
}