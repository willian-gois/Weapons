package br.com.finalelite.weapons.object;

import br.com.finalelite.weapons.Weapons;
import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Willian Gois (github/willgoix)
 */
public class WeaponItem {

    private ItemStack item;
    private Weapon weapon;
    private Integer level;
    private Double xp;

    public WeaponItem(ItemStack item, Weapon weapon) {
        this.item = item;
        this.weapon = weapon;
        this.level = 1;
        this.xp = 0.0;

        for (int i = item.getItemMeta().getLore().size() - 1; i > 0; i--) {
            String lore = item.getItemMeta().getLore().get(i);

            if (lore.startsWith(Weapon.LINE_LEVEL)) {
                this.level = Integer.valueOf(lore.split(Weapon.LINE_LEVEL)[1].split("/")[0]);
                this.xp = Double.valueOf(lore.split("[\\\\(\\\\)]")[1].split("/")[0]);
            }
        }
    }

    public static WeaponItem of(ItemStack item) {
        Weapon weapon = Weapons.getWeapons().getWeaponManager().getWeapon(item);
        return weapon == null ? null : new WeaponItem(item, weapon);
    }

    public ItemStack getItem() {
        return item;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void addLevel(Integer level) {
        setLevel(getLevel() + level);
    }

    public void setLevel(Integer level) {
        this.level = level > 100 ? 100 : level;
    }

    public Integer getLevel() {
        return level;
    }

    public void addXP(Double xp) {
        setXP(getXP() + xp);
    }

    public void setXP(Double xp) {
        this.xp = xp;

        while (this.xp >= (this.level * Weapon.XP_MULTIPLIER_PER_LEVEL)) {
            if (this.level >= 100) break;

            this.xp = this.xp - (this.level * Weapon.XP_MULTIPLIER_PER_LEVEL);
            addLevel(1);

            /* TODO: Notificar o novo nível? */
        }
    }

    public Double getXP() {
        return xp;
    }

    public void save() {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.getLore();

        for (int i = item.getItemMeta().getLore().size() - 1; i > 0; i--) {
            String lore = item.getItemMeta().getLore().get(i);

            if (lore.startsWith(Weapon.LINE_LEVEL)) {
                StringBuilder builder = new StringBuilder(Weapon.LINE_LEVEL);
                builder.append(getLevel()).append("/100");
                builder.append(" §8(").append(getXP()).append("/").append((this.level * Weapon.XP_MULTIPLIER_PER_LEVEL)).append(") XP");

                lores.set(i, builder.toString());
            } else if (lore.startsWith(Weapon.LINE_NEXT_LEVEL)) {
                lores.set(i, new StringBuilder(Weapon.LINE_NEXT_LEVEL).append(getProgressBar(getXP(), (this.level * Weapon.XP_MULTIPLIER_PER_LEVEL))).toString());
            }
        }

        meta.setLore(lores);
        item.setItemMeta(meta);
    }

    protected String getProgressBar(double value, double total) {
        final double BARS = 30.0;

        double bars_equation = (10 / (BARS/10));
        double percentage = ((value * 100) / total);
        double percentage_bars = percentage / bars_equation;
        StringBuilder builder = new StringBuilder("§7[");

        for (double i = 1.0; i < BARS; i++) {
            if (percentage_bars >= i) {
                builder.append("§a┃");
            } else {
                builder.append("§8┃");
            }
        }
        builder.append("§7] ");

        if (this.level >= 100 && this.xp >= (this.level * Weapon.XP_MULTIPLIER_PER_LEVEL)){
            builder.append("§aCONCLUÍDO!");
        }else{
            builder.append(ChatColor.WHITE).append(Math.round(percentage)).append("%");
        }
        return builder.toString();
    }
}
