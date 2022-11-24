package osrs.skills.herblore.aio.enums;

/**
 * @Author Ned
 */

public enum UnfPotion {

    GUAM_LEAF_POTION_UNF(0, "Guam potion (unf)", "Vial of water", 14, "Guam leaf", 14),
    MARRENTILL_POTION_UNF(1, "Marrentill potion (unf)", "Vial of water", 14, "Marrentill", 14),
    TARROMIN_POTION_UNF(2, "Tarromin potion (unf)", "Vial of water", 14, "Tarromin", 14),
    HARRALANDER_POTION_UNF(3, "Harralander potion (unf)", "Vial of water", 14, "Harralander", 14),
    RANARR_WEED_POTION_UNF(4, "Ranarr potion (unf)", "Vial of water", 14, "Ranarr weed", 14),
    TOADFLAX_POTION_UNF(5, "Toadflax potion (unf)", "Vial of water", 14, "Toadflax", 14),
    IRIT_LEAF_POTION_UNF(6, "Irit potion (unf)", "Vial of water", 14, "Irit leaf", 14),
    AVANTOE_POTION_UNF(7, "Avantoe potion (unf)", "Vial of water", 14, "Avantoe", 14),
    KWUARM_POTION_UNF(8, "Kwuarm potion (unf)", "Vial of water", 14, "Kwuarm", 14),
    SNAPDRAGON_POTION_UNF(9, "Snapdragon potion (unf)", "Vial of water", 14, "Snapdragon", 14),
    CADANTINE_POTION_UNF(10, "Cadantine potion (unf)", "Vial of water", 14, "Cadantine", 14),
    LANTADYME_POTION_UNF(11, "Lantadyme potion (unf)", "Vial of water", 14, "Lantadyme", 14),
    DWARF_WEED_POTION_UNF(12, "Dwarf weed potion (unf)", "Vial of water", 14, "Dwarf weed", 14),
    TORSTOL_POTION_UNF(13, "Torstol potion (unf)", "Vial of water", 14, "Torstol", 14);

    private final String unfPotion;
    private final String primary;
    private final String secondary;
    private final int id;
    private final int primaryQt;
    private final int secondaryQt;
    private final UnfPotion[] unfPotions;

    UnfPotion(final int id, final String unfPotion, final String primary, final int primaryQt, final String secondary, final int secondaryQt) {
        this.id = id;
        this.unfPotion = unfPotion;
        this.primary = primary;
        this.primaryQt = primaryQt;
        this.secondary = secondary;
        this.secondaryQt = secondaryQt;

        unfPotions = null;
    }

    public String getUnfPotion() {
        return unfPotion;
    }

    public String getPrimary() {
        return primary;
    }

    public String getSecondary() {
        return secondary;
    }

    public int getId() {
        return id;
    }

    public int getPrimaryQt() {
        return primaryQt;
    }

    public int getSecondaryQt() {
        return secondaryQt;
    }

    public UnfPotion[] getUnfPotions() {
        return unfPotions;
    }
}
