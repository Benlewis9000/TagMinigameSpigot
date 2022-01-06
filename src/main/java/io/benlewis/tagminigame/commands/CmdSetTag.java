package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSetTag extends TagCommand {

    public CmdSetTag(TagPlugin plugin) {
        super(plugin);
    }

    // TODO Should this command be in final release? Could cause confusion/undefined behaviour
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            Player player = plugin.getServer().getPlayerExact(args[0]);
            if (player != null) {
                if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")){
                    boolean tagged = Boolean.parseBoolean(args[1]);
                    if (plugin.getTagPlayerManager().hasPlayer(player)){
                        TagPlayer tagPlayer = plugin.getTagPlayerManager().getGPlayer(player);
                        // TODO update to use appropriate tag event, not just set straight here
                        tagPlayer.setTagged(tagged);
                        sender.sendMessage(ChatColor.GREEN + player.getName() + " has been set as " + (tagged ? "" : "not") + " tagged.");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "Player " + player.getName() + " is not in a game.");
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Invalid args: /settag <player> <true/false>");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "Player " + args[0] + " is not online.");
            }
        }
        else sender.sendMessage(ChatColor.RED + "Insufficient args: /settag <player> <true/false>");
        return true;
    }

}
