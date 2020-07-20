package br.com.finalelite.weapons.object;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AmuletItem {

    private final static BiConsumer<ItemStack, ItemMeta> BROKEN_AMULET_CONSUMER = (item, meta) -> {
        item.setType(Material.WHEAT_SEEDS);
        meta.setDisplayName(Text.translate("§8Pó de Amuleto Quebrado"));
        meta.getLore().clear();
        meta.getLore().addAll(Text.translate(
                " &7Esses fragmentos faziam",
                " &7parte de um grande amuleto!"
        ));
    };

    private final ItemStack item;
    private final Amulet amulet;
    private int remainingUses;

    public AmuletItem(ItemStack item, Amulet amulet) {
        this.amulet = amulet;
        this.item = item;
        this.remainingUses = 0;

        if (!item.hasItemMeta()) throw new UnsupportedOperationException("The item has no item meta.");

        for (int i = item.getItemMeta().getLore().size() - 1; i > 0; i--) {
            String lore = item.getItemMeta().getLore().get(i);

            if (lore.startsWith(Amulet.LINE_USES)) {
                this.remainingUses = Integer.parseInt(lore.split(Amulet.LINE_USES)[1]);
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
        ItemMeta meta = this.item.getItemMeta();
        List<String> lore = meta.getLore();

        for (int i = 0; i <= this.item.getItemMeta().getLore().size(); i++) {
            String line = this.item.getItemMeta().getLore().get(i);

            if (line.startsWith(Amulet.LINE_USES)) {

                if (getRemainingUses() > 1) {
                    lore.set(i, Amulet.LINE_USES + (this.remainingUses - 1));

                    this.remainingUses -= 1;
                } else {
                    BROKEN_AMULET_CONSUMER.accept(this.item, meta);
                }

                meta.setLore(lore);
                this.item.setItemMeta(meta);

                break;
            }
        }
    }
}
