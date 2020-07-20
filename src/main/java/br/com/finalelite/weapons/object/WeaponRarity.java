package br.com.finalelite.weapons.object;

import org.bukkit.ChatColor;

/**
 * @author Willian Gois (github/willgoix)
 */
public enum WeaponRarity {

    COMMOM("Comum", ChatColor.DARK_GRAY),
    UNCOMMOM("Incomum", ChatColor.BLUE),
    RARE("Raro", ChatColor.GREEN),
    EPIC("Épico", ChatColor.DARK_PURPLE),
    MYTHICAL("Mítico", ChatColor.RED),
    LEGENDARY("LENDÁRIO", ChatColor.GOLD);

    private String name;
    private ChatColor color;

    WeaponRarity(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public Integer getPriority() {
        return this.ordinal();
    }

    public String getName() {
        return this.name;
    }

    public String getColoredName() {
        return this.color + this.name;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public boolean bestThan(WeaponRarity rarity) {
        return getPriority() > rarity.getPriority();
    }

    public boolean bestOrEqualThan(WeaponRarity rarity) {
        return getPriority() >= rarity.getPriority();
    }

    public static WeaponRarity getRarityById(int id) {
        for (WeaponRarity rarities : values()) {
            if (rarities.getPriority().equals(id)) {
                return rarities;
            }
        }
        return null;
    }
}
