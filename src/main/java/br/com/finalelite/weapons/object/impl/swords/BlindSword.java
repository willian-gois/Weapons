package br.com.finalelite.weapons.object.impl.swords;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.object.impl.ProbabilityWeapon;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class BlindSword extends ProbabilityWeapon {

    public static final String ID = "BLIND_SWORD";

    public BlindSword() {
        super(ID);

        setName("Espada Cega");
        setDescription(Text.translate(
                " &7Use essa espada e mire direto",
                " &7nos olhos! Seu inimigo ficará",
                " &7com cegueira por 3 segundos.",
                " &a+0.25% &7de chance de eficácia",
                " &7por nível."));
        setRarity(WeaponRarity.UNCOMMOM);
        setType(WeaponType.SWORD);
        setItem(new ItemStack(Material.DIAMOND_SWORD));
    }

    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (other instanceof LivingEntity && chance(0.25, weapon.getLevel())) {
            ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 1));
        }
    }

    public Optional<WeaponItem> getUsingSwordOrAxe(Player player) {
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
}
