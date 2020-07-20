package br.com.finalelite.weapons.object.impl.leggings;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.object.impl.ProbabilityWeapon;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class EscapeLegging extends ProbabilityWeapon {

    public static final String ID = "LEGGING_HELMET";

    public EscapeLegging() {
        super(ID);

        setName("Calça de Fuga");
        setDescription(Text.translate(
                " &7Use essa armadura para ser",
                " &7lançado junto com uma pérola",
                " &7quando for atacado.",
                " &a+0.30% &7de chance de eficácia",
                " &7por nível."));
        setRarity(WeaponRarity.EPIC);
        setType(WeaponType.ARMOR_LEGGINGS);
        setItem(new ItemStack(Material.DIAMOND_LEGGINGS));
    }

    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (chance(0.30, weapon.getLevel())) {
            EnderPearl enderpearl = player.launchProjectile(EnderPearl.class, new Vector(0, 1.5, 0));
            enderpearl.addPassenger(player);
        }
    }
}
