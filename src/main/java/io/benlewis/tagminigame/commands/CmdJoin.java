package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

public class CmdJoin extends TagCommand {

    public CmdJoin(TagPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Insufficient args: /join <ID>");
            return true;
        }
        String gameIdArg = args[0];
        try {
            int gameId = Integer.parseInt(gameIdArg);
            TagGame game = plugin.getTagGameManager().getGame(gameId);
            game.register((Player) sender);
            sender.sendMessage(ChatColor.GREEN + "You have joined game " + gameIdArg + "!");
        }
        catch (NumberFormatException e){
            sender.sendMessage(ChatColor.RED + "The game ID must be a valid integer.");
        }
        catch (NoSuchElementException e){
            sender.sendMessage(ChatColor.RED + "No game with an ID of " + gameIdArg + " was found.");
        }
        catch (IllegalArgumentException e){
            sender.sendMessage(ChatColor.RED + "You are already in a game.");
        }
        return true;
    }
}
