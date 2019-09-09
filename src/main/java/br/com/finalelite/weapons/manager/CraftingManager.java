package br.com.finalelite.weapons.manager;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.object.Amulet;
import br.com.finalelite.weapons.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.List;

/**
 * @author Willian Gois (github/willgoix)
 */
public class CraftingManager {

    /**
     * Internal use in lore.
     **/
    public static final String CRAFTING_TAG_WEAPONS = "§r§9§r";
    public static final String CRAFTING_TAG_AMULET = "§r§1§r";

    public CraftingManager() {
        registerDefaults();
    }

    public void registerDefaults() {
        List<String> DEFAULT_LORE = Arrays.asList(
                " §7???",
                " ",
                " §7Raridade: §9?§5?§6?",
                " §7Nível: §8???§7/§8??? §8(0/50) XP",
                " §7Próximo: §7[§8┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃§7] §f0%",
                CRAFTING_TAG_WEAPONS);

        /* SWORD */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "weapon_sword"), new ItemBuilder(Material.DIAMOND_SWORD).name("§a§k................").lore(DEFAULT_LORE).build()).shape(
                "x",
                "x",
                "|")
                .setIngredient('x', Material.EMERALD)
                .setIngredient('|', Material.STICK));
        /* AXE */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "weapon_axe"), new ItemBuilder(Material.DIAMOND_AXE).name("§a§k................").lore(DEFAULT_LORE).build()).shape(
                "xx ",
                "x| ",
                " | ")
                .setIngredient('x', Material.EMERALD)
                .setIngredient('|', Material.STICK));

        /* HELMET */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "weapon_helmet"), new ItemBuilder(Material.DIAMOND_HELMET).name("§a§k................").lore(DEFAULT_LORE).build()).shape(
                "xxx",
                "x x")
                .setIngredient('x', Material.EMERALD));
        /* CHESTPLATE */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "weapon_chestplate"), new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("§a§k................").lore(DEFAULT_LORE).build()).shape(
                "x x",
                "xxx",
                "xxx")
                .setIngredient('x', Material.EMERALD));
        /* LEGGINGS */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "weapon_leggings"), new ItemBuilder(Material.DIAMOND_LEGGINGS).name("§a§k................").lore(DEFAULT_LORE).build()).shape(
                "xxx",
                "x x",
                "x x")
                .setIngredient('x', Material.EMERALD));
        /* BOOTS */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "weapon_boots"), new ItemBuilder(Material.DIAMOND_BOOTS).name("§a§k................").lore(DEFAULT_LORE).build()).shape(
                "x x",
                "x x")
                .setIngredient('x', Material.EMERALD));

        /* AMULET */
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(Weapons.getWeapons(), "amulet"), new ItemBuilder(Material.END_CRYSTAL).name("§a§k................").lore(
                " §7Carregue esse amuleto durante",
                " §7o forjamento de equipamentos",
                " §7e sua sorte irá aumentar: §8???%",
                " ",
                " §e§n§oObs:§r§7 A cada amuleto carregado,",
                " §7sua eficiência é reduzida pela metade.",
                " ",
                " §7Raridade: §8???",
                Amulet.LINE_USES + Amulet.AMULET_MAX_USE,
                CRAFTING_TAG_AMULET).build()).shape(
                " x ",
                "xxx",
                " x ")
                .setIngredient('x', Material.EMERALD));
    }
}
