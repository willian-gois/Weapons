package br.com.finalelite.weapons.object;

import br.com.finalelite.weapons.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Amulet {

    public static final Integer AMULET_MAX_USE = 5;
    public static final String LINE_USES = " §7Usos restantes: §8";

    private String name;
    private WeaponRarity rarity;
    private ItemStack item;

    public Amulet(String name, WeaponRarity rarity) {
        this.name = name;
        this.rarity = rarity;

        ItemBuilder builder = new ItemBuilder(Material.END_CRYSTAL)
                .name(String.format("%s%s", rarity.getColor(), name))
                .lore(
                        " §7Carregue esse amuleto durante",
                        " §7o forjamento de equipamentos ",
                        " §7e sua sorte irá aumentar: " + getRarity().getColor() + getLuckyByRarity(getRarity()) + "%",
                        " ",
                        " §e§n§oObs:§o§7 A cada amuleto carregado,",
                        " §7sua eficiência é reduzida pela metade.",
                        " ",
                        " §7Raridade: " + getRarity().getColoredName(),
                        LINE_USES + AMULET_MAX_USE);
        if (rarity == WeaponRarity.LEGENDARY) builder.glowing();

        this.item = builder.build();
    }

    public String getName() {
        return name;
    }

    public WeaponRarity getRarity() {
        return rarity;
    }

    public ItemStack getItem() {
        return item;
    }

    public static double getLuckyByRarity(WeaponRarity rarity) {
        switch (rarity) {
            case UNCOMMOM:
                return 0.45;
            case RARE:
                return 0.75;
            case EPIC:
                return 0.955;
            case MYTHICAL:
                return 1.15;
            case LEGENDARY:
                return 2;
            default: /* COMMON */
                return 0.25;
        }
    }
}
