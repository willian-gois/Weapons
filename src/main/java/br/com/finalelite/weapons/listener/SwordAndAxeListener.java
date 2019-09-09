package br.com.finalelite.weapons.listener;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.object.WeaponItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Willian Gois (github/willgoix)
 */
public class SwordAndAxeListener implements Listener {

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getDamager() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Entity victim = event.getEntity();

        Optional<WeaponItem> weapon = Weapons.getWeapons().getWeaponManager().getUsingSwordOrAxe(damager);
        if (weapon.isPresent())
            weapon.get().getWeapon().getHandler().handle(damager, victim, weapon.get(), event);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        Player player = event.getEntity().getKiller();

        if (player == null) return;

        Optional<WeaponItem> weapon = Weapons.getWeapons().getWeaponManager().getUsingSwordOrAxe(player);
        if (weapon.isPresent()) {

            /* Anti player free-kill system */
            if (victim instanceof Player) {
                StringBuilder stringBuilder = new StringBuilder(weapon.get().getWeapon().getName()).append(":").append(victim.getName());

                if (player.hasMetadata(stringBuilder.toString())) {
                    long killTimestamp = player.getMetadata(stringBuilder.toString()).get(0).asLong();

                    if ((System.currentTimeMillis() - killTimestamp) <= TimeUnit.MINUTES.toMillis(1)){
                        return;
                    }
                } else {
                    player.setMetadata(stringBuilder.toString(), new FixedMetadataValue(Weapons.getWeapons(), System.currentTimeMillis()));
                }
            }

            weapon.get().addXP(getExperienceGained(victim.getType()));
            weapon.get().save();
        }
    }

    private double getExperienceGained(EntityType type) {
        switch (type) {
            case PLAYER:
                return 5.0;

            case IRON_GOLEM:
                return 4.5;

            case ENDERMAN:
                return 4.0;
            case BLAZE:
                return 4.0;
            case SLIME:
                return 4.0;
            case MAGMA_CUBE:
                return 4.0;
            case PIG_ZOMBIE:
                return 4.0;

            case ZOMBIE:
                return 3.0;
            case SKELETON:
                return 3.0;
            case CAVE_SPIDER:
                return 3.0;
            case CREEPER:
                return 3.0;
            case POLAR_BEAR:
                return 3.0;

            case COW:
                return 2.0;
            case PIG:
                return 2.0;
            case CHICKEN:
                return 2.0;
            case SHEEP:
                return 2.0;
            case RABBIT:
                return 2.0;

            default:
                return 1.0;
        }
    }
}
