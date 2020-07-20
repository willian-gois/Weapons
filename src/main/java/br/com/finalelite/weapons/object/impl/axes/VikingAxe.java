package br.com.finalelite.weapons.object.impl.axes;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.object.impl.ProbabilityWeapon;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class VikingAxe extends ProbabilityWeapon {

    public static final String ID = "VIKING_AXE";

    public VikingAxe() {
        super(ID);

        setName("Machado Viking");
        setDescription(Text.translate(
                " &7Essa arma viking é de capaz",
                " &7de causar o DOBRO de dano em",
                " &7armaduras inimigas.",
                " &a+0.30% &7de chance de eficácia",
                " &7por nível."));
        setRarity(WeaponRarity.MYTHICAL);
        setType(WeaponType.AXE);
        setItem(new ItemStack(Material.DIAMOND_AXE));
    }

    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (other instanceof Player && chance(0.30, weapon.getLevel())) {
            for (ItemStack armors : ((Player) other).getInventory().getArmorContents()) {
                boolean damage = false;

                if (armors.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    damage = ThreadLocalRandom.current().nextInt(armors.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1) == 1;
                }

                if (damage) {
                    Damageable damageable = ((Damageable) armors);
                    damageable.setDamage(damageable.getDamage() + 1);
                }
            }
        }
    }
}
