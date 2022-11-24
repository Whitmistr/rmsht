package osrs.skills.herblore.aio.enums;

/**
 * @Author Ned
 */

public enum Potion {
    WINE(35, "Unfermented wine", "Grapes", 14, "Jug of water", 14),
    ATTACK_POTION(0, "Attack potion(3)", "Guam potion (unf)", 14, "Eye of newt", 14),
    ANTIPOISON_POTION(1, "Antipoison potion(3)", "Marrentill potion (unf)", 14, "Unicorn horn dust", 14),
    STRENGTH_POTION(2, "Strength potion(3)", "Tarromin potion (unf)", 14, "Limpwurt root", 14),
    SERUM_207_POTION(3, "Serum 207", "Tarromin potion (unf)", 14, "Ashes", 14),
    COMPOST_POTION(4, "Compost potion", "Harralander potion (unf)", 14, "Volcanic ash", 14),
    RESTORE_POTION(5, "Restore potion(3)", "Marrentill potion (unf)", 14, "Red spiders' eggs", 14),
    ENERGY_POTION(6, "Energy potion(3)", "Harralander potion (unf)", 14, "Chocolate dust", 14),
    DEFENCE_POTION(7, "Defence potion(3)", "Ranarr potion (unf)", 14, "White berries", 14),
    AGILITY_POTION(8, "Agility potion(3)", "Toadflax potion (unf)", 14, "Toad's legs", 14),
    COMBAT_POTION(9, "Combat potion(3)", "Harralander potion (unf)", 14, "Goat horn dust", 14),
    PRAYER_POTION(10, "Prayer potion(3)", "Ranarr potion (unf)", 14, "Snape grass", 14),
    SUPER_ATTACK_POTION(11, "Super attack(3)", "Irit potion (unf)", 14, "Eye of newt", 14),
    SUPER_ANTIPOISON_POTION(12, "Superantipoison(3)", "Irit potion (unf)", 14, "Unicorn horn dust", 14),
    FISHING_POTION(13, "Fishing potion(3)", "Avantoe potion (unf)", 14, "Snape grass", 14),
    SUPER_ENERGY_POTION(14, "Super energy(3)", "Avantoe potion (unf)", 14, "Mort myre fungi", 14),
    HUNTER_POTION(15, "Hunter potion(3)", "Avantoe potion (unf)", 14, "Kebbit teeth dust", 14),
    SUPER_STRENGTH_POTION(16, "Super strength(3)", "Kwuarm potion (unf)", 14, "Limpwurt root", 14),
    WEAPON_POISON_POTION(17, "Weapon poison", "Kwuam potion (unf)", 14, "Dragon scale dust", 14),
    SUPER_RESTORE_POTION(18, "Super restore(3)", "Snapdragon potion (unf)", 14, "Red spiders' eggs", 14),
    SUPER_DEFENCE_POTION(19, "Super defence(3)", "Cadantine potion (unf)", 14, "White berries", 14),
    ANTIDOTEPLUS_POTION(20, "Antidote+(3)", "Antidote+ (unf)", 14, "Yew roots", 14),
    ANTIFIRE_POTION(21, "Antifire potion(3)", "Lantadyme potion (unf)", 14, "Dragon scale dust", 14),
    RANGING_POTION(22, "Ranging potion(3)", "Dwarf weed potion (unf)", 14, "Wine of Zamorak", 14),
    MAGIC_POTION(23, "Magic potion(3)", "Lantadyme potion (unf)", 14, "Potato cactus", 14),
    ZAMORAK_BREW_POTION(24, "Zamorak brew(3)", "Torstol potion (unf)", 14, "Jangerberries", 14),
    ANTIDOTE_PLUS_PLUS_POTION(26, "Antidote++(3)", "Antidote++ (unf)", 14, "Magic roots", 14),
    WEAPON_POISON_PLUS_POTION(27, "Weapon poison(+)", "Weapon poison+ (unf)", 14, "Red spiders' eggs", 14),
    SARADOMIN_BREW_POTION(28, "Saradomin brew(3)", "Toadflax potion (unf)", 14, "Crushed bird's nest", 14),
    WEAPON_POISON_PLUS_PLUS_POTION(29, "Weapon poison(++)", "Weapon poison++ (unf)", 14, "Poison ivy berries", 14),
    STAMINA_POTION(30, "Stamina potion(4)", "Super energy(4)", 27, "Amylase crystal", 108),
    ANTI_VENOM_POTION(31, "Anti-venom(4)", "Antidote++(4)", 27, "Zulrah's scales", 540),
    EXTENDED_ANTIFIRE_POTION(32, "Extended antifire(4)", "Antifire potion(4)", 27, "Lava scale shard", 108),
    SUPER_ANTIFIRE_POTION(33, "Super antifire potion(4)", "Antifire potion(4)", 14, "Crushed superior dragon bones", 14),
    EXTENDED__SUPER_ANTIFIRE_POTION(34, "Extended super antifire(4)", "Super antifire potion(4)", 27, "Lava scale shard", 108);

    private final String finalPotion;
    private final String primary;
    private final String secondary;
    private final int id;
    private final int primaryQt;
    private final int secondaryQt;

    Potion(final int id, final String finalPotion, final String primary, final int primaryQt, final String secondary, final int secondaryQt) {
        this.id = id;
        this.finalPotion = finalPotion;
        this.primary = primary;
        this.primaryQt = primaryQt;
        this.secondary = secondary;
        this.secondaryQt = secondaryQt;

    }

    public String getFinalPotion() {
        return finalPotion;
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

}