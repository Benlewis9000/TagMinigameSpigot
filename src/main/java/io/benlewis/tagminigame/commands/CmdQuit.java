package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.events.PlayerQuitTagGameEvent;
import org.bukkit.Bukkit;
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
        if (sender instanceof Player){
            PlayerQuitTagGameEvent quitEvent = new PlayerQuitTagGameEvent((Player) sender);
            Bukkit.getPluginManager().callEvent(quitEvent);
            if (!quitEvent.getHandled()){
                sender.sendMessage(ChatColor.RED + "You are not in a game.");
            }
        }
        return true;
    }
}
