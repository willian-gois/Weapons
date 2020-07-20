package br.com.finalelite.weapons.listener;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.util.Using;
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

        Optional<WeaponItem> weapon = Using.getUsingSwordOrAxe(damager);
        if (weapon.isPresent())
            weapon.get().getWeapon().handle(damager, victim, weapon.get(), event);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        Player player = event.getEntity().getKiller();

        if (player == null) return;

        Optional<WeaponItem> weapon = Using.getUsingSwordOrAxe(player);
        if (weapon.isPresent()) {

            /* Breve sistema free-kill */
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
            case BLAZE:
            case SLIME:
            case MAGMA_CUBE:
            case PIG_ZOMBIE:
                return 4.0;

            case ZOMBIE:
            case SKELETON:
            case CAVE_SPIDER:
            case CREEPER:
            case POLAR_BEAR:
                return 3.0;

            case COW:
            case PIG:
            case CHICKEN:
            case SHEEP:
            case RABBIT:
                return 2.0;

            default:
                return 1.0;
        }
    }
}
