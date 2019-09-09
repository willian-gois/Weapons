package br.com.finalelite.weapons.object;

import br.com.finalelite.weapons.Weapons;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AmuletItem {

    private ItemStack item;
    private Amulet amulet;
    private Integer remainingUses;

    public AmuletItem(ItemStack item, Amulet amulet) {
        this.amulet = amulet;
        this.item = item;
        this.remainingUses = 0;

        for (int i = item.getItemMeta().getLore().size() - 1; i > 0; i--) {
            String lore = item.getItemMeta().getLore().get(i);

            if (lore.startsWith(Amulet.LINE_USES)) {
                this.remainingUses = Integer.valueOf(lore.split(Amulet.LINE_USES)[1]);
                break;
            }
        }
    }

    public static AmuletItem of(ItemStack item) {
        Amulet amulet = Weapons.getWeapons().getWeaponManager().getAmulet(item);
        return amulet == null ? null : new AmuletItem(item, amulet);
    }

    public ItemStack getItem() {
        return item;
    }

    public Amulet getAmulet() {
        return amulet;
    }

    public Integer getRemainingUses() {
        return remainingUses;
    }

    public void use() {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.getLore();

        for (int i = 0; i <= item.getItemMeta().getLore().size(); i++) {
            String lore = item.getItemMeta().getLore().get(i);

            if (lore.startsWith(Amulet.LINE_USES)) {

                if (getRemainingUses() > 1) {
                    lores.set(i, new StringBuilder(Amulet.LINE_USES).append(getRemainingUses() - 1).toString());

                    this.remainingUses -= 1;
                } else {
                    item.setType(Material.WHEAT_SEEDS);
                    meta.setDisplayName("§8Pó de Amuleto Quebrado");
                    lores.clear();
                    lores.addAll(Arrays.asList(" §7Esses fragmentos faziam", " §7parte de um grande amuleto!"));
                }

                meta.setLore(lores);
                item.setItemMeta(meta);

                break;
            }
        }
    }
}
