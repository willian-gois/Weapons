package br.com.finalelite.weapons.util;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class Using {

    public static Optional<WeaponItem> getUsingSwordOrAxe(Player player) {
        for (ItemStack item : new ItemStack[]{player.getInventory().getItemInMainHand(), player.getInventory().getItemInOffHand()}) {
            if (item != null && (item.getType().name().contains(WeaponType.SWORD.name()) || item.getType().name().contains(WeaponType.AXE.name())) && item.hasItemMeta()) {
                WeaponItem weaponItem = WeaponItem.of(item);

                if (weaponItem == null) {
                    continue;
                }

                return Optional.of(weaponItem);
            }
        }
        return Optional.empty();
    }

    public static Set<WeaponItem> getUsingArmors(Player player) {
        Set<WeaponItem> set = new HashSet<>();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.hasItemMeta()) {
                WeaponItem weaponItem = WeaponItem.of(item);

                if (weaponItem == null) {
                    continue;
                }

                set.add(weaponItem);
            }
        }
        return set;
    }
}
