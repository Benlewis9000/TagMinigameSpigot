package io.benlewis.tagminigame.commands;

import io.benlewis.tagminigame.TagPlugin;
import org.bukkit.command.CommandExecutor;

public abstract class TagCommand implements CommandExecutor {

    final TagPlugin plugin;

    public TagCommand(TagPlugin plugin){
        this.plugin = plugin;
    }

}
