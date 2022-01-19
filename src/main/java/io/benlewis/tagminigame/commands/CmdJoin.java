package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGamePhase;
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
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Insufficient args: /join <ID>");
            return true;
        }
        String gameIdArg = args[0];
        int gameId;
        try {
            gameId = Integer.parseInt(gameIdArg);
        }
        catch (NumberFormatException e){
            sender.sendMessage(ChatColor.RED + "Game ID \"" + gameIdArg + "\" is not a valid integer.");
            return true;
        }
        if (plugin.getPlayerDataManager().get(player).isInGame()){
            sender.sendMessage(ChatColor.RED + "You are already in a game.");
            return true;
        }
        if (!plugin.getTagGameManager().hasGame(gameId)) {
            sender.sendMessage(ChatColor.RED + "No game with an ID of " + gameIdArg + " was found.");
            return true;
        }
        TagGame game = plugin.getTagGameManager().getGame(gameId);
        if (game.isFull()){
            sender.sendMessage(ChatColor.RED + "This game is full.");
            return true;
        }
        if (game.getPhase() != TagGamePhase.LOBBY){
            sender.sendMessage(ChatColor.RED + "This game has already started.");
            return true;
        }
        try {
            game.register((Player) sender);
        }
        catch(Exception e){
            sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + " Failed to join game, " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
}
