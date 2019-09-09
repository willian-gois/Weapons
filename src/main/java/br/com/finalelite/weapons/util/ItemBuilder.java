package br.com.finalelite.weapons.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ItemBuilder {

    protected ItemStack item = null;

    public ItemBuilder() {
        item = new ItemStack(Material.AIR, 1);
    }

    public ItemBuilder(Material material) {
        item = new ItemStack(material, 1);
    }

    public ItemBuilder(ItemStack item) {
        this.item = item.clone();
    }

    public ItemBuilder type(Material type) {
        item.setType(type);
        return this;
    }

    /* Baseado na 1.8
    public ItemBuilder data(int value) {
        item.set(new MaterialData((short) value));
        return this;
    }
    */

    public ItemBuilder durability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemBuilder amount(int value) {
        item.setAmount(value);
        return this;
    }

    public ItemBuilder name(String name) {
        meta((meta) -> meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name)));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        meta((meta) -> {
            lore.forEach(s -> ChatColor.translateAlternateColorCodes('&', s));
            meta.setLore(lore);
        });
        return this;
    }

    public ItemBuilder lore(String... lines) {
        return lore(Stream.of(lines).collect(Collectors.toList()));
    }

    public ItemBuilder addLore(String... lines) {
        meta((meta) -> {
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            Arrays.asList(lines).forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));

            meta.setLore(lore);
        });
        return this;
    }

    public ItemBuilder removeLore(String line) {
        meta((meta) -> {
            if (meta.hasLore()) {
                List<String> lore = new ArrayList<>(meta.getLore());
                lore.remove(line);

                meta.setLore(lore);
            }
        });
        return this;
    }

    public ItemBuilder removeLore(int line) {
        meta((meta) -> {
            if (meta.hasLore()) {
                List<String> lore = new ArrayList<>(meta.getLore());
                lore.remove(line);

                meta.setLore(lore);
            }
        });
        return this;
    }

    public ItemBuilder glowing() {
        return enchant(Enchantment.DURABILITY, 1).flags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder enchant(Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemBuilder woolColor(DyeColor color) {
        if (!item.getType().name().contains("WOOL")) {
            throw new UnsupportedOperationException("Impossivel utilizar woolColor() para um item que nao seja la.");
        }
        item.setDurability(color.getDyeData());
        return this;
    }

    public ItemBuilder armorColor(Color color) {
        if (!item.getType().name().contains("LEATHER")) {
            throw new UnsupportedOperationException("Impossivel utilizar armorColor() para um item que nao seja couro.");
        }
        meta((meta) -> ((LeatherArmorMeta) meta).setColor(color));
        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        meta((meta) -> meta.addItemFlags(flags));
        return this;
    }

    public ItemBuilder unstackable() {
        Random RANDOM = new Random();
        char[] APPEND = new char[]{'§', '\0', '§', '\0', '§', '\0', '§', '\0'};

        APPEND[1] = (char) (48 + RANDOM.nextInt(10));
        APPEND[3] = (char) (48 + RANDOM.nextInt(10));
        APPEND[5] = (char) (48 + RANDOM.nextInt(10));
        APPEND[7] = (char) (48 + RANDOM.nextInt(10));

        addLore(String.valueOf(APPEND));
        return this;
    }

    public ItemStack build() {
        return item;
    }

    protected <T extends ItemMeta> ItemBuilder meta(Consumer<T> metaConsumer) {
        final ItemMeta meta = item.getItemMeta();
        metaConsumer.accept((T) meta);
        item.setItemMeta(meta);
        return this;
    }
}
