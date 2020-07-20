package br.com.finalelite.weapons.object.impl.helmets;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.object.impl.ProbabilityWeapon;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class VulcanicHelmet extends ProbabilityWeapon {

    public static final String ID = "VULCANIC_HELMET";

    public VulcanicHelmet() {
        super(ID);

        setName("Capacete Vulcânico");
        setDescription(Text.translate(
                " &7O indivíduo que se atrever a irritar",
                " &7a força vulcânica contida nessa",
                " &7armadura irá provocar lançamentos de",
                " &7bolas de fogo para todos os lados...",
                " &a+0.15% &7de chance de eficácia",
                " &7por nível."));
        setRarity(WeaponRarity.RARE);
        setType(WeaponType.ARMOR_HELMET);
        setItem(new ItemStack(Material.DIAMOND_HELMET));
    }

    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (chance(0.15, weapon.getLevel())) {
            for (double i = 0; i < 360.0; i += 45.0) {
                double cosx = Math.cos(Math.toRadians(i));
                double sinz = Math.sin(Math.toRadians(i));

                Fireball fireball = player.getWorld().spawn(player.getEyeLocation().add(cosx * 2, 0, sinz * 2), Fireball.class);
                Vector vector = player.getEyeLocation().getDirection();

                vector.setX(cosx);
                vector.setZ(sinz);

                fireball.setDirection(vector);
            }
        }
    }
}
