package br.com.finalelite.weapons;

import br.com.finalelite.weapons.command.WeaponsCommand;
import br.com.finalelite.weapons.listener.ArmorListener;
import br.com.finalelite.weapons.listener.CraftingListener;
import br.com.finalelite.weapons.listener.RepairListener;
import br.com.finalelite.weapons.listener.SwordAndAxeListener;
import br.com.finalelite.weapons.manager.CraftingManager;
import br.com.finalelite.weapons.manager.WeaponManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Weapons extends JavaPlugin {

    private static Weapons weapons;

    private WeaponManager weaponManager;

    @Override
    public void onEnable() {
        weapons = this;
        this.weaponManager = new WeaponManager();
        new CraftingManager();

        Bukkit.getPluginCommand("weapons").setExecutor(new WeaponsCommand());
        Bukkit.getPluginManager().registerEvents(new ArmorListener(), this);
        Bukkit.getPluginManager().registerEvents(new CraftingListener(), this);
        Bukkit.getPluginManager().registerEvents(new RepairListener(), this);
        Bukkit.getPluginManager().registerEvents(new SwordAndAxeListener(), this);
    }

    public WeaponManager getWeaponManager() {
        return weaponManager;
    }

    public static Weapons getWeapons() {
        return weapons;
    }
}
