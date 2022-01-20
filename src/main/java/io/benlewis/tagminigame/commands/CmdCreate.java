package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;

@org.bukkit.plugin.java.annotation.command.Command(name="create", desc="Create a new tag game.", permission="tag.create")
@Permission(name="tag.create", desc="Can create a game.", defaultValue= PermissionDefault.OP)
public class CmdCreate extends TagCommand {

    public CmdCreate(TagPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Insufficient args: /create <minNoPlayers> <maxNoPlayers>");
            return true;
        }
        try {
            int minNoPlayers;
            int maxNoPlayers;
            try {
                minNoPlayers = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e){
                sender.sendMessage(ChatColor.RED + "Min number of players may not be \"" + args[0] + "\". " +
                        "Please use a valid integer.");
                return true;
            }
            try {
                maxNoPlayers = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e){
                sender.sendMessage(ChatColor.RED + "Max number of players may not be \"" + args[1] + "\". " +
                        "Please use a valid integer.");
                return true;
            }
            if (minNoPlayers > maxNoPlayers){
                sender.sendMessage(ChatColor.RED + "Min number of players may not be larger than max number of players.");
                return true;
            }
            TagGame game = plugin.getTagGameManager().createGame(minNoPlayers, maxNoPlayers);
            sender.sendMessage(ChatColor.GREEN + "Game created with ID " + game.getId() + "!");
        }
        catch (Exception e){
            sender.sendMessage(ChatColor.RED + "Game creation failed: " + e.getMessage() + ".");
            e.printStackTrace();
        }
        return true;
    }
}
