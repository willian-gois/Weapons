package br.com.finalelite.weapons.object;

import br.com.finalelite.weapons.object.handler.WeaponHandler;
import br.com.finalelite.weapons.util.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Weapon {

    /* O XP necessário pra o próximo nível será calculado da seguinte forma: (nível) * (multiplicador) */
    public static final Double XP_MULTIPLIER_PER_LEVEL = 50.0;
    public static final String LINE_LEVEL = " §7Nível: §e";
    public static final String LINE_NEXT_LEVEL = " §7Próximo: ";

    private String name;
    private List<String> description;
    private ItemStack item;
    private WeaponType type;
    private WeaponRarity rarity;
    private WeaponHandler handler;

    /**
     * @param handler Executed when the weapon's item is used for your function, by example: when attacking with sword
     **/
    public Weapon(String name, List<String> description, ItemStack item, WeaponType type, WeaponRarity rarity, WeaponHandler handler) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.rarity = rarity;
        this.handler = handler;

        this.item = new ItemBuilder(item)
                .name(rarity.getColor() + name)
                .lore(description)
                .addLore(" ",
                        " §7Raridade: " + rarity.getColoredName(),
                        LINE_LEVEL + "1/100 §8(0/50) XP",
                        LINE_NEXT_LEVEL + "§7[§8┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃§7] §f0%",
                        " ")
                .build();
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getItem() {
        return item;
    }

    public WeaponType getType() {
        return type;
    }

    public WeaponRarity getRarity() {
        return rarity;
    }

    public WeaponHandler getHandler() {
        return handler;
    }
}
