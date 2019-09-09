package br.com.finalelite.weapons.listener;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.object.WeaponItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ArmorListener implements Listener {

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player)) return;

        Entity damager = event.getDamager();
        Player victim = (Player) event.getEntity();

        Set<WeaponItem> weapons = Weapons.getWeapons().getWeaponManager().getUsingArmors(victim);
        if (!weapons.isEmpty()) {
            for (WeaponItem weapon : weapons) {
                weapon.getWeapon().getHandler().handle(victim, damager, weapon, event);

                weapon.addXP(event.getDamage());
                weapon.save();
            }
        }
    }
}
