package io.benlewis.tagminigame;

import io.benlewis.tagminigame.commands.CmdCreate;
import io.benlewis.tagminigame.commands.CmdJoin;
import io.benlewis.tagminigame.commands.CmdQuit;
import io.benlewis.tagminigame.commands.CmdSetTag;
import io.benlewis.tagminigame.game.data.PlayerDataManager;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import io.benlewis.tagminigame.listeners.EntityDamageByEntityListener;
import io.benlewis.tagminigame.listeners.PlayerJoinEventListener;
import io.benlewis.tagminigame.listeners.PlayerQuitEventListener;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;

@Plugin(name="TagPlugin", version="1.0.0")
@Author("Anarchist")
@Command(name="create", desc="Create a new tag game.", permission="tag.create")
@Permission(name="tag.create", desc="Can create a game.", defaultValue= PermissionDefault.OP)
@Command(name="join", desc="Join a game.", permission="tag.join")
@Permission(name="tag.join", desc="Can join a game.", defaultValue= PermissionDefault.TRUE)
@Command(name="quit", desc="Leave a game.", permission="tag.quit")
@Permission(name="tag.quit", desc="Can leave a game.", defaultValue= PermissionDefault.TRUE)
@Command(name="settag", desc="Forcibly set a players tagged status.", permission="tag.settag")
@Permission(name="tag.settag", desc="Can forcibly set a players tagged status", defaultValue= PermissionDefault.OP)
public class TagPlugin extends JavaPlugin {

    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private final TagGameManager tagGameManager = new TagGameManager(this);
    private final boolean debug = true;

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public TagGameManager getTagGameManager() {
        return tagGameManager;
    }

    // Main class must have no arg constructor
    public TagPlugin() {}

    // Required constructor for MockBukkit
    protected TagPlugin(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
        super(loader, descriptionFile, dataFolder, file);
    }

    @Override
    public void onEnable(){
        this.getCommand("create").setExecutor(new CmdCreate(this));
        this.getCommand("join").setExecutor(new CmdJoin(this));
        this.getCommand("quit").setExecutor(new CmdQuit(this));
        this.getCommand("settag").setExecutor(new CmdSetTag(this));

        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(playerDataManager, tagGameManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(playerDataManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(playerDataManager, tagGameManager), this);
    }

    @Override
    public void onDisable(){

    }

    public void debug(String msg){
        // TODO replace with proper config/options
        if (this.debug) getLogger().info("[DEBUG] " + msg);
    }

}
