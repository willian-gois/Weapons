package br.com.finalelite.weapons.object.impl.chestplates;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.object.impl.ProbabilityWeapon;
import br.com.finalelite.weapons.util.Text;
import br.com.finalelite.weapons.util.Using;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class MagicalChestplate extends ProbabilityWeapon {

    public static final String ID = "MAGICAL_CHESTPLATE";

    public MagicalChestplate() {
        super(ID);

        setName("Peitoral Mágico");
        setDescription(Text.translate(
                " &7Esse peitoral possui propriedades",
                " &7mágicas, e é capaz de anular",
                " &7os efeitos de todas as armas.",
                " &a+0.25% &7de chance de eficácia",
                " &7por nível."));
        setRarity(WeaponRarity.MYTHICAL);
        setType(WeaponType.ARMOR_CHESTPLATE);
        setItem(new ItemStack(Material.DIAMOND_CHESTPLATE));
    }

    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (other instanceof Player && chance(0.25, weapon.getLevel())) {
            Optional<WeaponItem> otherWeapon = Using.getUsingSwordOrAxe((Player) other);

            if (otherWeapon.isPresent()) {
                player.damage(event.getDamage(), other);
                event.setCancelled(true);
            }
        }
    }
}