package br.com.finalelite.weapons.object;

import br.com.finalelite.weapons.util.ItemBuilder;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Willian Gois (github/willgoix)
 */
public abstract class Weapon {

    /* O XP necessário pra o próximo nível será calculado da seguinte forma: (nível) * (multiplicador) */
    public static final Double XP_MULTIPLIER_PER_LEVEL = 50.0;
    public static final String LINE_LEVEL = Text.translate(" &7Nível: &e");
    public static final String LINE_NEXT_LEVEL = Text.translate(" &7Próximo: ");

    private final String id;
    private String name;
    private List<String> description;
    private ItemStack item;
    private WeaponType type;
    private WeaponRarity rarity;

    /**
     * @param id          Um identificador único para a arma.
     * @param name        O nome da arma.
     * @param description A descrição da arma que aparecerá na lore.
     * @param item        O respectivo item bukkit da arma.
     * @param type        O tipo da arma.
     * @param rarity      A raridade da arma.
     */
    public Weapon(String id, String name, List<String> description, ItemStack item, WeaponType type, WeaponRarity rarity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rarity = rarity;
        this.setItem(item);
    }

    public Weapon(String id) {
        this(id, null, null, null, null, null);
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setItem(ItemStack item) {
        this.item = new ItemBuilder(item)
                .name(rarity.getColor() + name)
                .lore(description)
                .addLore(" ",
                        " &7Raridade: " + rarity.getColoredName(),
                        LINE_LEVEL + "1/100 &8(0/50) XP",
                        LINE_NEXT_LEVEL + "&7[&8┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃┃&7] &f0%",
                        " ")
                .build();
    }

    public ItemStack getItem() {
        return item;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public WeaponType getType() {
        return type;
    }

    public void setRarity(WeaponRarity rarity) {
        this.rarity = rarity;
    }

    public WeaponRarity getRarity() {
        return rarity;
    }

    /**
     * Quando usado em {@link WeaponType#SWORD} ou {@link WeaponType#AXE}
     * <p>
     * @param player O jogador que atacou a entidade {@code target} e utilizou a arma.
     * @param other  A entidade que foi atacado pelo {@code player}.
     * @param weapon A arma especial.
     * @param event  O evento do dano.
     * <p>
     * <p>
     * Quando usado em {@link WeaponType#ARMOR_HELMET}, {@link WeaponType#ARMOR_CHESTPLATE}, {@link WeaponType#ARMOR_LEGGINGS} ou {@link WeaponType#ARMOR_BOOTS}
     * <p>
     * @param player O jogador que foi atacado pela entidade {@code target} e está utilizando a arma.
     * @param other  A entidade que atacou o {@code player}.
     * @param weapon A arma especial.
     * @param event  O evento do dano.
     */
    public abstract void handle(Player player, Entity other, WeaponItem weapon, EntityDamageByEntityEvent event);
}
