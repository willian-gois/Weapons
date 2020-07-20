package br.com.finalelite.weapons.object;

import org.bukkit.Material;

/**
 * @author Willian Gois (github/willgoix)
 */
public enum WeaponType {

    SWORD(Material.DIAMOND_SWORD),
    AXE(Material.DIAMOND_AXE),

    ARMOR_HELMET(Material.DIAMOND_HELMET),
    ARMOR_CHESTPLATE(Material.DIAMOND_CHESTPLATE),
    ARMOR_LEGGINGS(Material.DIAMOND_LEGGINGS),
    ARMOR_BOOTS(Material.DIAMOND_BOOTS);

    private Material defaultMaterial;

    WeaponType(Material defaultMaterial) {
        this.defaultMaterial = defaultMaterial;
    }

    public Material getDefaultMaterial() {
        return this.defaultMaterial;
    }

    public static WeaponType getWeaponType(Material material) {
        for (WeaponType types : values()) {
            if (types.getDefaultMaterial().equals(material)) {
                return types;
            }
        }
        return null;
    }
}
