package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.data.DataPlayer;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdQuit extends TagCommand{

    public CmdQuit(TagPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        DataPlayer dataPlayer = plugin.getPlayerDataManager().get(player);
        if (!dataPlayer.isInGame()) {
            sender.sendMessage(ChatColor.RED + "You are not in a game.");
            return true;
        }
        int gameId = dataPlayer.getGameId();
        TagGame game = plugin.getTagGameManager().getGame(gameId);
        game.playerQuit(player);
        return true;
    }
}
