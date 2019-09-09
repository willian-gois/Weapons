package br.com.finalelite.weapons.object.handler;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author Willian Gois (github/willgoix)
 */
@FunctionalInterface
public interface WeaponHandler {

    /**
     * When in {@link WeaponType#SWORD} or {@link WeaponType#AXE}
     * <p>
     * @param player The player that attacked the {@code target} and is carrying the weapon
     * @param other The entity that was attacked by {@code player}
     * @param weapon The special weapon
     * @param event The damage event
     * <p>
     * <p>
     * When in {@link WeaponType#ARMOR_HELMET}, {@link WeaponType#ARMOR_CHESTPLATE}, {@link WeaponType#ARMOR_LEGGINGS} or {@link WeaponType#ARMOR_BOOTS}
     * <p>
     * @param player The player that was attacked by {@code target} and is carrying the weapon
     * @param other The entity that attacked the {@code player}
     * @param weapon The special weapon
     * @param event The damage event
     */
    void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event);
}