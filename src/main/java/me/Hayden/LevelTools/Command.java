package me.Hayden.LevelTools;

import me.Hayden.LevelTools.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        Player player = (Player) commandSender;

        if (args.length == 0) {
            player.sendMessage(Util.translateHexCodes("&aLevelTools " + Main.getInstance().getDescription().getVersion()));
            player.sendMessage(Util.translateHexCodes("&7https://tinyurl.com/leveltools"));
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("leveltools.reload")) {
                player.sendMessage(ChatColor.RED + "No permission");
                return false;
            }
            Main.getInstance().reloadConfig();
            player.sendMessage(ChatColor.GREEN + "Config reloaded");
        }

        return false;
    }
}
