package br.com.finalelite.weapons.object.impl.boots;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.object.impl.ProbabilityWeapon;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Willian Gois (github/willgoix) - 20/07/2020
 */
public class WaterBoots extends ProbabilityWeapon {

    public static final String ID = "WATER_BOOT";

    public WaterBoots() {
        super(ID);

        setName("Botas de Água");
        setDescription(Text.translate(
                " &7Usando essas botas o portador",
                " &7tem chance de não entrar em chamas",
                " &7por conta de encantos especiais.",
                " &a+0.40% &7de chance de eficácia",
                " &7por nível."));
        setRarity(WeaponRarity.COMMOM);
        setType(WeaponType.ARMOR_BOOTS);
        setItem(new ItemStack(Material.DIAMOND_BOOTS));
    }

    @Override
    public void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event) {
        if (chance(0.40, weapon.getLevel())) {
            player.setFireTicks(0);
        }
    }
}
