package br.com.finalelite.weapons.object;

import org.bukkit.ChatColor;

/**
 * @author Willian Gois (github/willgoix)
 */
public enum WeaponRarity {

    COMMOM(0, "Comum", ChatColor.DARK_GRAY), UNCOMMOM(1, "Incomum", ChatColor.BLUE), RARE(2, "Raro", ChatColor.GREEN), EPIC(3, "Épico", ChatColor.DARK_PURPLE), MYTHICAL(4, "Mítico", ChatColor.RED), LEGENDARY(5, "LENDÁRIO", ChatColor.GOLD);

    private Integer id;
    private String name;
    private ChatColor color;

    WeaponRarity(Integer id, String name, ChatColor color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColoredName(){
        return color + name;
    }

    public ChatColor getColor() {
        return color;
    }

    public boolean bestThan(WeaponRarity rarity) {
        return getId() > rarity.getId();
    }

    public boolean bestOrEqualThan(WeaponRarity rarity) {
        return getId() >= rarity.getId();
    }

    public static WeaponRarity getRarityById(Integer id) {
        for (WeaponRarity rarities : values()) {
            if (rarities.getId() == id) {
                return rarities;
            }
        }
        return null;
    }
}
