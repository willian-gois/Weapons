package br.com.finalelite.weapons.manager;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.object.Amulet;
import br.com.finalelite.weapons.object.Weapon;
import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import br.com.finalelite.weapons.object.WeaponType;
import br.com.finalelite.weapons.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
            addAmulet(new Amulet(String.format("%sAmuleto %s", rarity.getColor(), rarity.getName()), rarity));
        }

        /* WEAPONS */
        /* SWORDS */
        addWeapon(new Weapon("Espada Gulosa",
                Arrays.asList(
                        " §7Uma batalha pode dar muita fome, ",
                        " §7então nada melhor que utilizar",
                        " §7uma espada para recuperá-la!",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_SWORD),
                WeaponType.SWORD,
                WeaponRarity.COMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel()))
                        player.setFoodLevel(20);
                }
        ));

        addWeapon(new Weapon("Espada Cega",
                Arrays.asList(
                        " §7Use essa espada e mire direto",
                        " §7nos olhos! Seu inimigo ficará",
                        " §7com cegueira por 3 segundos.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_SWORD),
                WeaponType.SWORD,
                WeaponRarity.UNCOMMOM,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.25, weapon.getLevel()))
                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 1));
                }
        ));

        addWeapon(new Weapon("Espada das Nuvens",
                Arrays.asList(
                        " §7Quem cruzar o caminho dessa",
                        " §7espada irá conhecer as nuvens!",
                        " §7Digo... serão lançados para cima!",
                        " §a+0.20% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_SWORD),
                WeaponType.SWORD,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.20, weapon.getLevel()))
                        other.setVelocity(other.getVelocity().add(new Vector(0, 1.5, 0)));
                }
        ));

        addWeapon(new Weapon("Espada Arranca-Coração",
                Arrays.asList(
                        " §7Espada usada para matar dragões!",
                        " §7Mas também quebra um galho em humanos...",
                        " §7Use-a nos seus inimigos e poderá",
                        " §7ficar com os pontos de vida tirados.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_SWORD),
                WeaponType.SWORD,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.25, weapon.getLevel()))
                        player.setHealth(player.getHealth() + event.getDamage() >= 20 ? 20 : player.getHealth() + event.getDamage());
                }
        ));

        addWeapon(new Weapon("Espada de Gelo",
                Arrays.asList(
                        " §7Essa arma é capaz de transferir",
                        " §7toda sua frieza para um inimigo",
                        " §7deixando-o congelado e sem poder revidar.",
                        " §a+0.10% §7de chance de eficácia por nível.",
                        " §a+5 segundos §7de efeito a cada 25 níveis."),
                new ItemStack(Material.DIAMOND_SWORD),
                WeaponType.SWORD,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        Integer effectSeconds = weapon.getLevel() < 25 ? 5 : weapon.getLevel() < 50 ? 10 : weapon.getLevel() < 75 ? 15 : 20;

                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectSeconds * 20, 4));
                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, effectSeconds * 20, 4));
                    }
                }
        ));

        addWeapon(new Weapon("Espada de Lâmina Dupla",
                Arrays.asList(
                        " §7Dobre o dano causado ao usar",
                        " §7essa espada.",
                        " §a+0.35% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_SWORD),
                WeaponType.SWORD,
                WeaponRarity.LEGENDARY,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.35, weapon.getLevel()))
                        event.setDamage(event.getDamage() * 2);
                }
        ));

        /* AXES */
        addWeapon(new Weapon("Machado Veloz",
                Arrays.asList(
                        " §7Com seu peso perfeitamente equilibrado,",
                        " §7esse arma pode ser muito rápido",
                        " §7e dar efeito de quebra-rápida",
                        " §7para quem portá-lo.",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_AXE),
                WeaponType.AXE,
                WeaponRarity.COMMOM,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.15, weapon.getLevel())) {
                        Integer effectLevel = weapon.getLevel() < 25 ? 1 : weapon.getLevel() < 50 ? 2 : weapon.getLevel() < 75 ? 3 : 4;

                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, effectLevel));
                    }
                }
        ));

        addWeapon(new Weapon("Machado Sangrento",
                Arrays.asList(
                        " §7Esse machado é capaz de adentrar",
                        " §7profundamente o seu inimigo, ",
                        " §7podendo deixá-lo sangrando por",
                        " §78 segundos.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_AXE),
                WeaponType.AXE,
                WeaponRarity.UNCOMMOM,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        new Task(task -> {
                            if ((System.currentTimeMillis() - task.getStartedAt()) > TimeUnit.SECONDS.toMillis(8))
                                task.cancel();

                            ((LivingEntity) other).damage(2, player);
                        }).repeat(20).run(Weapons.getWeapons());
                    }
                }
        ));

        addWeapon(new Weapon("Machado Pesado",
                Arrays.asList(
                        " §7Por conta seu grande peso, esse",
                        " §7machado pode lançar para frente",
                        " §7o indivíduo que portá-lo.",
                        " §a+0.40% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_AXE),
                WeaponType.AXE,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.40, weapon.getLevel()))
                        player.setVelocity(player.getEyeLocation().getDirection().setY(0.5).multiply(0.7));
                }
        ));

        addWeapon(new Weapon("Machado Furioso",
                Arrays.asList(
                        " §7Essa arma aprisiona um alma furiosa",
                        " §7que uma vez já foi humano. Essa alma",
                        " §7transfere sua força para o indivíduo",
                        " §7que portá-la, podendo dar efeito de",
                        " §7força por 15 segundos.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível.",
                        " §a1 nível §7de efeito a cada 20 níveis."),
                new ItemStack(Material.DIAMOND_AXE),
                WeaponType.AXE,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, weapon.getLevel() < 30 ? 0 : weapon.getLevel() < 60 ? 1 : 2));
                    }
                }
        ));

        addWeapon(new Weapon("Machado Viking",
                Arrays.asList(
                        " §7Essa arma viking é de capaz",
                        " §7de causar o DOBRO de dano em",
                        " §7armaduras inimigas.",
                        " §a+0.30% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_AXE),
                WeaponType.AXE,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof Player && chance(0.30, weapon.getLevel())) {
                        for (ItemStack armors : ((Player) other).getInventory().getArmorContents()) {
                            boolean damage = false;

                            if (armors.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                damage = RANDOM.nextInt(armors.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1) == 1;
                            }

                            if (damage) {
                                Damageable damageable = ((Damageable) armors);
                                damageable.setDamage(damageable.getDamage() + 1);
                            }
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Machado Viking Lord",
                Arrays.asList(
                        " §7Essa arma viking é de capaz",
                        " §7de causar o DOBRO de dano em",
                        " §7armaduras inimigas, ignorando",
                        " §7totalmente encantos de proteção.",
                        " §a+0.30% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_AXE),
                WeaponType.AXE,
                WeaponRarity.LEGENDARY,
                (player, other, weapon, event) -> {
                    if (other instanceof Player && chance(0.30, weapon.getLevel())) {
                        for (ItemStack armors : ((Player) other).getInventory().getArmorContents()) {
                            Damageable damageable = ((Damageable) armors);
                            damageable.setDamage(damageable.getDamage() + 1);
                        }
                    }
                }
        ));

        /* HELMETS */
        addWeapon(new Weapon("Capacete de Água",
                Arrays.asList(
                        " §7Usando esse capacete o portador",
                        " §7tem chance de não entrar em chamas",
                        " §7por conta de encantos especiais.",
                        " §a+0.40% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.COMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.40, weapon.getLevel()))
                        player.setFireTicks(0);
                }
        ));

        addWeapon(new Weapon("Capacete de Fogo",
                Arrays.asList(
                        " §7O inimigo desse capacete entrará",
                        " §7em chamas infinitas ao atacá-lo.",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+20 segundos §7de efeito a cada 20",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.UNCOMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        Integer effectSeconds = (weapon.getLevel() < 20 ? 20 : weapon.getLevel() < 40 ? 40 : weapon.getLevel() < 60 ? 60 : weapon.getLevel() < 80 ? 80 : 100);
                        other.setFireTicks(effectSeconds * 20);
                    }
                }
        ));

        addWeapon(new Weapon("Capacete de Espinhos Venenosos",
                Arrays.asList(
                        " §7Esse capacete é feito de grandes",
                        " §7espinhos, quem atacá-lo irá ficar",
                        " §7envenenado por alguns segundos.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+5 segundos §7de efeito a cada 25",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        Integer effectSeconds = weapon.getLevel() < 25 ? 5 : weapon.getLevel() < 50 ? 10 : weapon.getLevel() < 75 ? 15 : 20;

                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.POISON, effectSeconds * 20, 1));
                    }
                }
        ));

        addWeapon(new Weapon("Capacete Vulcânico",
                Arrays.asList(
                        " §7O indivíduo que se atrever a irritar",
                        " §7a força vulcânica contida nessa",
                        " §7armadura irá provocar lançamentos de",
                        " §7bolas de fogo para todos os lados...",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        for (double i = 0; i < 360.0; i += 45.0){
                            double cosx = Math.cos(Math.toRadians(i));
                            double sinz = Math.sin(Math.toRadians(i));

                            Fireball fireball = player.getWorld().spawn(player.getEyeLocation().add(cosx * 2, 0, sinz * 2), Fireball.class);
                            Vector vector = player.getEyeLocation().getDirection();

                            vector.setX(cosx);
                            vector.setZ(sinz);

                            fireball.setDirection(vector);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Capacete de Fuga",
                Arrays.asList(
                        " §7Use essa armadura para ser",
                        " §7lançado junto com uma pérola",
                        " §7quando for atacado.",
                        " §a+0.30% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.30, weapon.getLevel())) {
                        EnderPearl enderpearl = player.launchProjectile(EnderPearl.class, new Vector(0, 1.5, 0));
                        enderpearl.addPassenger(player);
                    }
                }
        ));

        addWeapon(new Weapon("Capacete Repulsivo",
                Arrays.asList(
                        " §7Os inimigos que atacarem o",
                        " §7portador desse capacete serão",
                        " §7lançados para trás.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.10, weapon.getLevel()))
                        other.setVelocity(player.getEyeLocation().getDirection().setY(1.5).multiply(1));
                }
        ));

        addWeapon(new Weapon("Capacete Mágico",
                Arrays.asList(
                        " §7Esse capacete possui propriedades",
                        " §7mágicas, e é capaz de anular",
                        " §7os efeitos de todas as armas.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof Player && chance(0.25, weapon.getLevel())) {
                        Optional<WeaponItem> otherWeapon = getUsingSwordOrAxe((Player) other);

                        if (otherWeapon.isPresent()){
                            player.damage(event.getDamage(), other);
                            event.setCancelled(true);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Capacete do Capiroto",
                Arrays.asList(
                        " §7Esse capacete é capaz de invocar",
                        " §7três porcos-zumbis na frente",
                        " §7seus inimigos.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        for (int i = -90; i < 90; i += 90){
                            double angle = Math.toRadians(player.getEyeLocation().getYaw() + i);
                            Location location = player.getEyeLocation().add(Math.cos(angle) * 3, 0, Math.sin(angle) * 3);

                            player.getWorld().spawn(location, PigZombie.class, pigZombie -> {
                                pigZombie.setHealth(30.0);
                                pigZombie.setAngry(true);
                                pigZombie.setTarget((LivingEntity) other);
                            });
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Capacete Mega-Proteção",
                Arrays.asList(
                        " §7Esse capacete é capaz de dobrar",
                        " §7o efeito do encanto de proteção",
                        " §7contido nele.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_HELMET),
                WeaponType.ARMOR_HELMET,
                WeaponRarity.LEGENDARY,
                (player, other, weapon, event) -> {
                    if (weapon.getItem().containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) && chance(0.25, weapon.getLevel())) {
                        event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 2);
                    }
                }
        ));

        /* CHESTPLATES */
        addWeapon(new Weapon("Peitoral de Água",
                Arrays.asList(
                        " §7Usando esse peitoral o portador",
                        " §7tem chance de não entrar em chamas",
                        " §7por conta de encantos especiais.",
                        " §a+0.40% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.COMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.40, weapon.getLevel()))
                        player.setFireTicks(0);
                }
        ));

        addWeapon(new Weapon("Peitoral de Fogo",
                Arrays.asList(
                        " §7O inimigo desse peitoral entrará",
                        " §7em chamas infinitas ao atacá-lo.",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+20 segundos §7de efeito a cada 20",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.UNCOMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        Integer effectSeconds = (weapon.getLevel() < 20 ? 20 : weapon.getLevel() < 40 ? 40 : weapon.getLevel() < 60 ? 60 : weapon.getLevel() < 80 ? 80 : 100);
                        other.setFireTicks(effectSeconds * 20);
                    }
                }
        ));

        addWeapon(new Weapon("Peitoral de Espinhos Venenosos",
                Arrays.asList(
                        " §7Esse peitoral é feito de grandes",
                        " §7espinhos, quem atacá-lo irá ficar",
                        " §7envenenado por alguns segundos.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+5 segundos §7de efeito a cada 25",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        Integer effectSeconds = weapon.getLevel() < 25 ? 5 : weapon.getLevel() < 50 ? 10 : weapon.getLevel() < 75 ? 15 : 20;

                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.POISON, effectSeconds * 20, 1));
                    }
                }
        ));

        addWeapon(new Weapon("Peitoral Vulcânico",
                Arrays.asList(
                        " §7O indivíduo que se atrever a irritar",
                        " §7a força vulcânica contida nessa",
                        " §7armadura irá provocar lançamentos de",
                        " §7bolas de fogo para todos os lados...",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        for (double i = 0; i < 360.0; i += 45.0){
                            double cosx = Math.cos(Math.toRadians(i));
                            double sinz = Math.sin(Math.toRadians(i));

                            Fireball fireball = player.getWorld().spawn(player.getEyeLocation().add(cosx * 2, 0, sinz * 2), Fireball.class);
                            Vector vector = player.getEyeLocation().getDirection();

                            vector.setX(cosx);
                            vector.setZ(sinz);

                            fireball.setDirection(vector);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Peitoral de Fuga",
                Arrays.asList(
                        " §7Use essa armadura para ser",
                        " §7lançado junto com uma pérola",
                        " §7quando for atacado.",
                        " §a+0.30% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.30, weapon.getLevel())) {
                        EnderPearl enderpearl = player.launchProjectile(EnderPearl.class, new Vector(0, 1.5, 0));
                        enderpearl.addPassenger(player);
                    }
                }
        ));

        addWeapon(new Weapon("Peitoral Repulsivo",
                Arrays.asList(
                        " §7Os inimigos que atacarem o",
                        " §7portador desse peitoral serão",
                        " §7lançados para trás.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.10, weapon.getLevel()))
                        other.setVelocity(player.getEyeLocation().getDirection().setY(1.5).multiply(1));
                }
        ));

        addWeapon(new Weapon("Peitoral Mágico",
                Arrays.asList(
                        " §7Esse peitoral possui propriedades",
                        " §7mágicas, e é capaz de anular",
                        " §7os efeitos de todas as armas.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof Player && chance(0.25, weapon.getLevel())) {
                        Optional<WeaponItem> otherWeapon = getUsingSwordOrAxe((Player) other);

                        if (otherWeapon.isPresent()){
                            player.damage(event.getDamage(), other);
                            event.setCancelled(true);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Peitoral do Capiroto",
                Arrays.asList(
                        " §7Esse peitoral é capaz de invocar",
                        " §7três porcos-zumbis na frente",
                        " §7seus inimigos.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        for (int i = -90; i < 90; i += 90){
                            double angle = Math.toRadians(player.getEyeLocation().getYaw() + i);
                            Location location = player.getEyeLocation().add(Math.cos(angle) * 3, 0, Math.sin(angle) * 3);

                            player.getWorld().spawn(location, PigZombie.class, pigZombie -> {
                                pigZombie.setHealth(30.0);
                                pigZombie.setAngry(true);
                                pigZombie.setTarget((LivingEntity) other);
                            });
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Peitoral Mega-Proteção",
                Arrays.asList(
                        " §7Esse peitoral é capaz de dobrar",
                        " §7o efeito do encanto de proteção",
                        " §7contido nele.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                WeaponType.ARMOR_CHESTPLATE,
                WeaponRarity.LEGENDARY,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 2);
                    }
                }
        ));

        /* LEGGINGS */
        addWeapon(new Weapon("Calça de Água",
                Arrays.asList(
                        " §7Usando essa calça o portador",
                        " §7tem chance de não entrar em chamas",
                        " §7por conta de encantos especiais.",
                        " §a+0.40% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.COMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.40, weapon.getLevel()))
                        player.setFireTicks(0);
                }
        ));

        addWeapon(new Weapon("Calça de Fogo",
                Arrays.asList(
                        " §7O inimigo dessa calça entrará",
                        " §7em chamas infinitas ao atacá-lo.",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+20 segundos §7de efeito a cada 20",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.UNCOMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        Integer effectSeconds = (weapon.getLevel() < 20 ? 20 : weapon.getLevel() < 40 ? 40 : weapon.getLevel() < 60 ? 60 : weapon.getLevel() < 80 ? 80 : 100);
                        other.setFireTicks(effectSeconds * 20);
                    }
                }
        ));

        addWeapon(new Weapon("Calça de Espinhos Venenosos",
                Arrays.asList(
                        " §7Essa calça é feita de grandes",
                        " §7espinhos, quem atacá-lo irá ficar",
                        " §7envenenado por alguns segundos.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+5 segundos §7de efeito a cada 25",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        Integer effectSeconds = weapon.getLevel() < 25 ? 5 : weapon.getLevel() < 50 ? 10 : weapon.getLevel() < 75 ? 15 : 20;

                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.POISON, effectSeconds * 20, 1));
                    }
                }
        ));

        addWeapon(new Weapon("Calça Vulcânica",
                Arrays.asList(
                        " §7O indivíduo que se atrever a irritar",
                        " §7a força vulcânica contida nessa",
                        " §7armadura irá provocar lançamentos de",
                        " §7bolas de fogo para todos os lados...",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        for (double i = 0; i < 360.0; i += 45.0){
                            double cosx = Math.cos(Math.toRadians(i));
                            double sinz = Math.sin(Math.toRadians(i));

                            Fireball fireball = player.getWorld().spawn(player.getEyeLocation().add(cosx * 2, 0, sinz * 2), Fireball.class);
                            Vector vector = player.getEyeLocation().getDirection();

                            vector.setX(cosx);
                            vector.setZ(sinz);

                            fireball.setDirection(vector);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Calça de Fuga",
                Arrays.asList(
                        " §7Use essa armadura para ser",
                        " §7lançado junto com uma pérola",
                        " §7quando for atacado.",
                        " §a+0.30% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.30, weapon.getLevel())) {
                        EnderPearl enderpearl = player.launchProjectile(EnderPearl.class, new Vector(0, 1.5, 0));
                        enderpearl.addPassenger(player);
                    }
                }
        ));

        addWeapon(new Weapon("Calça Repulsiva",
                Arrays.asList(
                        " §7Os inimigos que atacarem o",
                        " §7portador dessa calça serão",
                        " §7lançados para trás.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.10, weapon.getLevel()))
                        other.setVelocity(player.getEyeLocation().getDirection().setY(1.5).multiply(1));
                }
        ));

        addWeapon(new Weapon("Calça Mágico",
                Arrays.asList(
                        " §7Essa calça possui propriedades",
                        " §7mágicas, e é capaz de anular",
                        " §7os efeitos de todas as armas.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof Player && chance(0.25, weapon.getLevel())) {
                        Optional<WeaponItem> otherWeapon = getUsingSwordOrAxe((Player) other);

                        if (otherWeapon.isPresent()){
                            player.damage(event.getDamage(), other);
                            event.setCancelled(true);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Calça do Capiroto",
                Arrays.asList(
                        " §7Essa calça é capaz de invocar",
                        " §7três porcos-zumbis na frente",
                        " §7seus inimigos.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        for (int i = -90; i < 90; i += 90){
                            double angle = Math.toRadians(player.getEyeLocation().getYaw() + i);
                            Location location = player.getEyeLocation().add(Math.cos(angle) * 3, 0, Math.sin(angle) * 3);

                            player.getWorld().spawn(location, PigZombie.class, pigZombie -> {
                                pigZombie.setHealth(30.0);
                                pigZombie.setAngry(true);
                                pigZombie.setTarget((LivingEntity) other);
                            });
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Calça Mega-Proteção",
                Arrays.asList(
                        " §7Essa calça é capaz de dobrar",
                        " §7o efeito do encanto de proteção",
                        " §7contido nele.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_LEGGINGS),
                WeaponType.ARMOR_LEGGINGS,
                WeaponRarity.LEGENDARY,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 2);
                    }
                }
        ));

        /* BOOTS */
        addWeapon(new Weapon("Botas de Água",
                Arrays.asList(
                        " §7Usando essas botas o portador",
                        " §7tem chance de não entrar em chamas",
                        " §7por conta de encantos especiais.",
                        " §a+0.40% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.COMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.40, weapon.getLevel()))
                        player.setFireTicks(0);
                }
        ));

        addWeapon(new Weapon("Botas de Fogo",
                Arrays.asList(
                        " §7O inimigo dessas botas entrará",
                        " §7em chamas infinitas ao atacá-lo.",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+20 segundos §7de efeito a cada 20",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.UNCOMMOM,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        Integer effectSeconds = (weapon.getLevel() < 20 ? 20 : weapon.getLevel() < 40 ? 40 : weapon.getLevel() < 60 ? 60 : weapon.getLevel() < 80 ? 80 : 100);
                        other.setFireTicks(effectSeconds * 20);
                    }
                }
        ));

        addWeapon(new Weapon("Botas de Espinhos Venenosos",
                Arrays.asList(
                        " §7Essas botas são feitas de grandes",
                        " §7espinhos, quem atacá-lo irá ficar",
                        " §7envenenado por alguns segundos.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível.",
                        " §a+5 segundos §7de efeito a cada 25",
                        " §7níveis."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        Integer effectSeconds = weapon.getLevel() < 25 ? 5 : weapon.getLevel() < 50 ? 10 : weapon.getLevel() < 75 ? 15 : 20;

                        ((LivingEntity) other).addPotionEffect(new PotionEffect(PotionEffectType.POISON, effectSeconds * 20, 1));
                    }
                }
        ));

        addWeapon(new Weapon("Botas Vulcânicas",
                Arrays.asList(
                        " §7O indivíduo que se atrever a irritar",
                        " §7a força vulcânica contida nessa",
                        " §7armadura irá provocar lançamentos de",
                        " §7bolas de fogo para todos os lados...",
                        " §a+0.15% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.RARE,
                (player, other, weapon, event) -> {
                    if (chance(0.15, weapon.getLevel())) {
                        for (double i = 0; i < 360.0; i += 45.0){
                            double cosx = Math.cos(Math.toRadians(i));
                            double sinz = Math.sin(Math.toRadians(i));

                            Fireball fireball = player.getWorld().spawn(player.getEyeLocation().add(cosx * 2, 0, sinz * 2), Fireball.class);
                            Vector vector = player.getEyeLocation().getDirection();

                            vector.setX(cosx);
                            vector.setZ(sinz);

                            fireball.setDirection(vector);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Botas de Fuga",
                Arrays.asList(
                        " §7Use essa armadura para ser",
                        " §7lançado junto com uma pérola",
                        " §7quando for atacado.",
                        " §a+0.30% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.30, weapon.getLevel())) {
                        EnderPearl enderpearl = player.launchProjectile(EnderPearl.class, new Vector(0, 1.5, 0));
                        enderpearl.addPassenger(player);
                    }
                }
        ));

        addWeapon(new Weapon("Botas Repulsivas",
                Arrays.asList(
                        " §7Os inimigos que atacarem o",
                        " §7portador dessas botas serão",
                        " §7lançados para trás.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.EPIC,
                (player, other, weapon, event) -> {
                    if (chance(0.10, weapon.getLevel()))
                        other.setVelocity(player.getEyeLocation().getDirection().setY(1.5).multiply(1));
                }
        ));

        addWeapon(new Weapon("Botas Mágicas",
                Arrays.asList(
                        " §7Essas botas possuem propriedades",
                        " §7mágicas, e é capaz de anular",
                        " §7os efeitos de todas as armas.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof Player && chance(0.25, weapon.getLevel())) {
                        Optional<WeaponItem> otherWeapon = getUsingSwordOrAxe((Player) other);

                        if (otherWeapon.isPresent()){
                            player.damage(event.getDamage(), other);
                            event.setCancelled(true);
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Botas do Capiroto",
                Arrays.asList(
                        " §7Essas botas são capazes de",
                        " §7invocar três porcos-zumbis",
                        " §7na frente dos seus inimigos.",
                        " §a+0.10% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.MYTHICAL,
                (player, other, weapon, event) -> {
                    if (other instanceof LivingEntity && chance(0.10, weapon.getLevel())) {
                        for (int i = -90; i < 90; i += 90){
                            double angle = Math.toRadians(player.getEyeLocation().getYaw() + i);
                            Location location = player.getEyeLocation().add(Math.cos(angle) * 3, 0, Math.sin(angle) * 3);

                            player.getWorld().spawn(location, PigZombie.class, pigZombie -> {
                                pigZombie.setHealth(30.0);
                                pigZombie.setAngry(true);
                                pigZombie.setTarget((LivingEntity) other);
                            });
                        }
                    }
                }
        ));

        addWeapon(new Weapon("Botas Mega-Proteção",
                Arrays.asList(
                        " §7Essas botas são capaz de dobrar",
                        " §7o efeito do encanto de proteção",
                        " §7contido nele.",
                        " §a+0.25% §7de chance de eficácia",
                        " §7por nível."),
                new ItemStack(Material.DIAMOND_BOOTS),
                WeaponType.ARMOR_BOOTS,
                WeaponRarity.LEGENDARY,
                (player, other, weapon, event) -> {
                    if (chance(0.25, weapon.getLevel())) {
                        event.setDamage(DamageModifier.ARMOR, event.getDamage(DamageModifier.ARMOR) / 2);
                    }
                }
        ));
    }

    private boolean chance(Double percentage, Integer level) {
        return RANDOM.nextInt(10) <= (percentage * level) / 100;
    }

    public double getExperienceNecessary(WeaponItem weaponItem) {
        return weaponItem.getLevel() * Weapon.XP_MULTIPLIER_PER_LEVEL;
    }

    public Optional<WeaponItem> getUsingSwordOrAxe(Player player) {
        for (ItemStack item : new ItemStack[]{player.getInventory().getItemInMainHand(), player.getInventory().getItemInOffHand()}) {
            if (item != null && (item.getType().name().contains(WeaponType.SWORD.name()) || item.getType().name().contains(WeaponType.AXE.name())) && item.hasItemMeta()) {
                WeaponItem weaponItem = WeaponItem.of(item);
                if (weaponItem == null)
                    continue;

                return Optional.ofNullable(weaponItem);
            }
        }
        return Optional.empty();
    }

    public Set<WeaponItem> getUsingArmors(Player player) {
        Set<WeaponItem> set = new HashSet<>();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.hasItemMeta()) {
                WeaponItem weaponItem =  WeaponItem.of(item);
                if (weaponItem == null)
                    continue;

                set.add(weaponItem);
            }
        }
        return set;
    }

    public void addWeapon(Weapon weapon) {
        if (weapons.containsKey(weapon.getName().toLowerCase()))
            throw new RuntimeException(String.format("A weapon '%s' ja existe.", weapon.getName().toLowerCase()));

        weapons.put(weapon.getName().toLowerCase(), weapon);
    }

    @Deprecated
    /** @deprecated Hmmm, this shit no sounds good... **/
    public Weapon getWeapon(ItemStack item) {
        if (!item.hasItemMeta()) return null;
        return weapons.values().stream().filter(weapon -> weapon.getItem().getType() == item.getType() && item.getItemMeta().getDisplayName().equals(weapon.getItem().getItemMeta().getDisplayName())).findAny().orElse(null);
    }

    public Weapon getWeapon(String name) {
        return weapons.get(name.toLowerCase());
    }

    public List<Weapon> getWeapons(WeaponType type, WeaponRarity rarity) {
        return weapons.values().stream().filter(weapon -> weapon.getType() == type && weapon.getRarity() == rarity).collect(Collectors.toList());
    }

    public Map<String, Weapon> getWeapons() {
        return weapons;
    }

    /* ------ */

    public void addAmulet(Amulet amulet) {
        if (amulets.containsKey(amulet.getName().toLowerCase()))
            throw new RuntimeException(String.format("O amuleto '%s' ja existe.", amulet.getName().toLowerCase()));

        amulets.put(amulet.getName().toLowerCase(), amulet);
    }

    @Deprecated
    /** @deprecated Hmmm, this shit no sounds good... **/
    public Amulet getAmulet(ItemStack item) {
        if (!item.hasItemMeta()) return null;
        return amulets.values().stream().filter(amulet -> item.getItemMeta().getDisplayName().equals(amulet.getItem().getItemMeta().getDisplayName())).findFirst().orElse(null);
    }

    public Amulet getAmulet(String name) {
        return amulets.get(name.toLowerCase());
    }

    public Amulet getAmulet(WeaponRarity rarity) {
        return amulets.values().stream().filter(amulet -> amulet.getRarity() == rarity).findFirst().orElse(null);
    }

    public Map<String, Amulet> getAmulets() {
        return amulets;
    }
}
