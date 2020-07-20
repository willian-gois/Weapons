package br.com.finalelite.weapons.manager;

import br.com.finalelite.weapons.object.Amulet;
import br.com.finalelite.weapons.object.Weapon;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Willian Gois (github/willgoix)
 */
public class WeaponManager {

    private final Random RANDOM = new Random();

    private Map<String, Weapon> weapons = new HashMap<>();
    private Map<String, Amulet> amulets = new HashMap<>();

    public WeaponManager() {
        registerDefaults();
    }

    private void registerDefaults() {
        /* AMULETS */
        for (WeaponRarity rarity : WeaponRarity.values()) {
            addAmulet(new Amulet(
                    rarity.name() + "_AMULET",
                    String.format("%sAmuleto %s", rarity.getColor(), rarity.getName()),
                    rarity
            ));
        }

        /* SWORDS */


        /* AXES */

        /* HELMETS */

        /* CHESTPLATES */

        /* LEGGINGS */

        /* BOOTS */

    }

    public void addWeapon(Weapon weapon) {
        if (this.weapons.containsKey(weapon.getId().toLowerCase())) {
            throw new RuntimeException(String.format("Weapon with ID '%s' already exists.", weapon.getName().toLowerCase()));
        }

        this.weapons.put(weapon.getId().toLowerCase(), weapon);
    }

    public Weapon getWeapon(String id) {
        return this.weapons.get(id.toLowerCase());
    }

    /*
     * TODO: Melhorar desempenho desses tipos de verificações. Possivelmente inserindo tags nbt no item armazenado e comparando.
     */
    public Weapon getWeapon(ItemStack item) {
        if (!item.hasItemMeta()) {
            return null;
        }

        return this.weapons.values().stream().filter(weapon -> weapon.getItem().getType().equals(item.getType()) && item.getItemMeta().getDisplayName().equals(weapon.getItem().getItemMeta().getDisplayName())).findAny().orElse(null);
    }

    public List<Weapon> getWeapons(WeaponType type, WeaponRarity rarity) {
        return this.weapons.values().stream().filter(weapon -> weapon.getType().equals(type) && weapon.getRarity().equals(rarity)).collect(Collectors.toList());
    }

    public Map<String, Weapon> getWeapons() {
        return this.weapons;
    }

    public void addAmulet(Amulet amulet) {
        if (this.amulets.containsKey(amulet.getId().toLowerCase()))
            throw new RuntimeException(String.format("Amulet with ID '%s' already exists.", amulet.getName().toLowerCase()));

        this.amulets.put(amulet.getId().toLowerCase(), amulet);
    }

    public Amulet getAmulet(String id) {
        return this.amulets.get(id.toLowerCase());
    }

    /*
     * TODO: Melhorar desempenho desses tipos de verificações. Possivelmente inserindo tags nbt no item armazenado e comparando.
     */
    public Amulet getAmulet(ItemStack item) {
        if (!item.hasItemMeta()) {
            return null;
        }
        return this.amulets.values().stream().filter(amulet -> item.getItemMeta().getDisplayName().equals(amulet.getItem().getItemMeta().getDisplayName())).findFirst().orElse(null);
    }

    public Amulet getAmulet(WeaponRarity rarity) {
        return this.amulets.values().stream().filter(amulet -> amulet.getRarity().equals(rarity)).findFirst().orElse(null);
    }

    public Map<String, Amulet> getAmulets() {
        return this.amulets;
    }
}
