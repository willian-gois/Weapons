package br.com.finalelite.weapons.listener;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.manager.CraftingManager;
import br.com.finalelite.weapons.object.Amulet;
import br.com.finalelite.weapons.object.AmuletItem;
import br.com.finalelite.weapons.object.Weapon;
import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.util.Chance;
import br.com.finalelite.weapons.util.Chance.NumberChance;
import br.com.finalelite.weapons.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author Willian Gois (github/willgoix)
 */
public class CraftingListener implements Listener {

    private static final Double PERCENTAGE_AMULET_FAIL = 40.0;
    private static final String[] BOOM_MESSAGES = new String[]{
            "Oh não! Parece que ocorreu uma fissão dos átomos do plutónio.",
            "Boom! Acho que esse plutónio estava instável...",
            "Nossa, que bagunça! Acho que... algo sobrecarregou...",
            "Que desastre! Talvez você deva ter mais cuidado ao mexer com plutõnio!",
            "Mas que azar! Vamos tentar de novo, agora eu estou confiante!",
            "Amuletos, amuletos... mágicos como um... BOOM!"
    };

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getRecipe().getResult();

        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            if (item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1).equals(CraftingManager.CRAFTING_TAG_WEAPONS)) {
                if (checkBugs(event)) return;

                List<Weapon> weapons = Weapons.getWeapons().getWeaponManager().getWeapons(WeaponType.getWeaponType(item.getType()), sortRarity(calculeLucky(player)));
                if (weapons.isEmpty()) { /* Causado quando não há itens da raridade sorteada e do material do item criado. */
                    player.sendMessage("§c(!) Ops! Parece que temos um problema interno :c. Entre em contato com um staffer.");

                    event.setResult(Result.DENY);
                    event.setCancelled(true);
                    return;
                }
                Weapon weapon = weapons.get(new Random().nextInt(weapons.size()));

                if (weapon.getRarity().bestOrEqualThan(WeaponRarity.EPIC))
                    makeParticles(event.getInventory().getLocation());

                event.getInventory().setResult(WeaponItem.of(weapon.getItem()).getItem());
            } else if (item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1).equals(CraftingManager.CRAFTING_TAG_AMULET)) {
                if (checkBugs(event)) return;

                Random random = new Random();
                if (random.nextInt(10) <= (PERCENTAGE_AMULET_FAIL / 100)) { /* Uma vez em quatro */
                    player.sendMessage(" ");
                    player.sendMessage(String.format("%s%s", "§c(!) ", BOOM_MESSAGES[random.nextInt(BOOM_MESSAGES.length)]));
                    player.sendMessage(" ");

                    event.getInventory().setMatrix(new ItemStack[]{null, null, null, null, null, null, null, null, null});
                    event.getInventory().setResult(null);

                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 5f, 10f);
                    player.getWorld().createExplosion(event.getInventory().getLocation(), 2.0f);
                } else {
                    Amulet amulet = Weapons.getWeapons().getWeaponManager().getAmulet(sortRarity(0.0));
                    if (amulet == null) {
                        player.sendMessage("§c(!) Ops! Parece que temos um problema interno :c. Entre em contato com um staffer.");

                        event.setResult(Result.DENY);
                        event.setCancelled(true);
                        return;
                    }

                    if (amulet.getRarity().bestOrEqualThan(WeaponRarity.EPIC))
                        makeParticles(event.getInventory().getLocation());

                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5f, 10f);
                    event.getInventory().setResult(AmuletItem.of(new ItemBuilder(amulet.getItem()).unstackable().build()).getItem());
                }
            }
        }
    }

    private boolean checkBugs(CraftItemEvent event) {
        if (event.getSlotType() == SlotType.RESULT) {
            if (event.getClick().isShiftClick()) {
                event.setResult(Result.DENY);
                event.setCancelled(true);

                event.getWhoClicked().sendMessage("§c(!) Você não pode coletar esse item usando shift.");
                return true;
            } else if (event.getAction() == InventoryAction.NOTHING) {
                event.setResult(Result.DENY);
                event.setCancelled(true);
                return true;
            }
        }
        return false;
    }

    private double calculeLucky(Player player) {
        double lucky = 0.0;

        int i = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;

            AmuletItem amuletItem = AmuletItem.of(item);
            if (amuletItem == null) continue;
            i++;

            amuletItem.use();
            lucky += Amulet.getLuckyByRarity(amuletItem.getAmulet().getRarity()) / i;
        }
        return lucky;
    }

    private WeaponRarity sortRarity(double lucky) {
        /* TODO: Pensar em uma fórmula melhorar para o sistema de sorte. */

        HashMap<WeaponRarity, NumberChance> values = new HashMap<>();
        values.put(WeaponRarity.COMMOM, new NumberChance(0, 37 - lucky));                     /* 37% */
        values.put(WeaponRarity.UNCOMMOM, new NumberChance(38 - lucky, 66 - lucky * 2));      /* 28% */
        values.put(WeaponRarity.RARE, new NumberChance(67 - lucky * 2, 82 - lucky * 4));      /* 15% */
        values.put(WeaponRarity.EPIC, new NumberChance(83 - lucky * 4, 92 - lucky * 2));      /* 9% */
        values.put(WeaponRarity.MYTHICAL, new NumberChance(93 - lucky * 2, 97 - lucky));      /* 4% */
        values.put(WeaponRarity.LEGENDARY, new NumberChance(98 - lucky, 100));                /* 2% */

        return new Chance<WeaponRarity>().getWinner(values);
    }

    private void makeParticles(Location location) {
        for (int i = -360; i < 360; i += 5) {
            if (i < 0) i = Math.abs(i);

            location.add(Math.cos(Math.toRadians(i)) * 1.5, 0.25, Math.sin(Math.toRadians(i)) * 1.5);

            location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 1);
            //location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, 0, 0, 0, new Particle.DustOptions(Color.GREEN, 1.0f));
        }
    }
}
