package br.com.finalelite.weapons.listener;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.util.Using;
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

        Entity attacker = event.getDamager();
        Player victim = (Player) event.getEntity();

        Set<WeaponItem> weapons = Using.getUsingArmors(victim);
        if (!weapons.isEmpty()) {
            for (WeaponItem weapon : weapons) {
                weapon.getWeapon().handle(victim, attacker, weapon, event);

                weapon.addXP(event.getDamage());
                weapon.save();
            }
        }
    }
}
