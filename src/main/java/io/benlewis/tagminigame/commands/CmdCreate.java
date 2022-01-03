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
        if (sender.isOp() || sender.hasPermission("tag.create")){
            try {
                TagGame game = plugin.getTagGameManager().createGame();
                sender.sendMessage(ChatColor.GREEN + "Game created with ID " + game.getId() + "!");
            }
            catch (Exception e){
                sender.sendMessage(ChatColor.RED + "Game creation failed: " + e.getMessage() + ".");
                e.printStackTrace();
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to create games.");
        }
        return true;
    }
}
