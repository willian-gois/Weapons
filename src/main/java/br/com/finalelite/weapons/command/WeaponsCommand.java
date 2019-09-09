package br.com.finalelite.weapons.command;

import br.com.finalelite.weapons.Weapons;
import br.com.finalelite.weapons.object.Amulet;
import br.com.finalelite.weapons.object.Weapon;
import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.object.WeaponRarity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Willian Gois (github/willgoix)
 */
public class WeaponsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) return false;

        if (!sender.isOp()) {
            sender.sendMessage("§cVocê não tem permissão para executar esse comando.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length <= 1) {
            player.sendMessage("");
            player.sendMessage("§a/weapons get <nome> §8- §7Pegue uma arma.");
            player.sendMessage("§a/weapons getamulet <raridade> §8- §7Pegue um amuleto.");
            player.sendMessage("§a/weapons setlevel <level> §8- §7Mudar o nível da arma que segura.");
            player.sendMessage("§a/weapons setxp <xp> §8- §7Mudar o XP da arma que segura.");
            player.sendMessage(" §8Raridades: 0-5");
        } else {
            if (args[0].equalsIgnoreCase("get")) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    stringBuilder.append(args[i]);

                    if (i != args.length - 1) stringBuilder.append(" ");
                }
                Weapon weapon = Weapons.getWeapons().getWeaponManager().getWeapon(stringBuilder.toString());

                if (weapon == null) {
                    player.sendMessage(String.format("§cA arma '%s' não está registrada.", stringBuilder.toString()));
                    return false;
                }

                player.getInventory().addItem(weapon.getItem());
                player.sendMessage(String.format("§aVocê pegou a arma '%s'", stringBuilder.toString()));

            } else if (args[0].equalsIgnoreCase("getamulet")) {
                try {
                    Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cRaridade precisa ser um número de 0 á 5.");
                    return false;
                }
                Integer rarityId = Integer.valueOf(args[1]);

                if (rarityId < 0 || rarityId > 5) {
                    player.sendMessage("§cRaridade precisa ser um número de 0 á 5.");
                    return false;
                }

                WeaponRarity rarity = WeaponRarity.getRarityById(rarityId);
                Amulet amulet = Weapons.getWeapons().getWeaponManager().getAmulet(rarity);

                if (amulet == null) {
                    player.sendMessage(String.format("§cNão há nenhum amuleto registrado com a raridade '%s'.", rarity.getName()));
                    return false;
                }

                player.getInventory().addItem(amulet.getItem());
                player.sendMessage(String.format("§aVocê pegou o amuleto '%s'", amulet.getName()));

            } else if (args[0].equalsIgnoreCase("setlevel")) {
                if (player.getInventory().getItemInMainHand() == null) {
                    player.sendMessage("§cVocê precisa estar com algum item na sua mão.");
                    return false;
                }
                WeaponItem weaponItem = WeaponItem.of(player.getInventory().getItemInMainHand());

                if (weaponItem == null) {
                    player.sendMessage("§cO item que você está segurando não é uma arma.");
                    return false;
                }

                try {
                    Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cO nível precisa ser um número de 0 á 100.");
                    return false;
                }
                Integer level = Integer.valueOf(args[1]);

                if (level < 0 || level > 100) {
                    player.sendMessage("§cO nível precisa ser um número de 0 á 100.");
                    return false;
                }

                weaponItem.setLevel(level);
                weaponItem.save();
                player.sendMessage(String.format("§aO nível da arma foi definido para %s.", level));

            } else if (args[0].equalsIgnoreCase("setxp")) {
                if (player.getInventory().getItemInMainHand() == null) {
                    player.sendMessage("§cVocê precisa estar com algum item na sua mão.");
                    return false;
                }
                WeaponItem weaponItem = WeaponItem.of(player.getInventory().getItemInMainHand());

                if (weaponItem == null) {
                    player.sendMessage("§cO item que você está segurando não é uma arma.");
                    return false;
                }

                try {
                    Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cO XP precisa ser um número maior que 0.0.");
                    return false;
                }
                Double xp = Double.valueOf(args[1]);

                if (xp < 0.0) {
                    player.sendMessage("§cO XP precisa ser um número maior que 0.0.");
                    return false;
                }

                weaponItem.setXP(xp);
                weaponItem.save();
                player.sendMessage(String.format("§aO XP da arma foi definido para %s.", xp));
            }
        }
        return false;
    }
}
