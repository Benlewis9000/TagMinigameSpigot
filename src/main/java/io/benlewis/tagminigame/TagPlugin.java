package io.benlewis.tagminigame;

import io.benlewis.tagminigame.commands.CmdCreate;
import io.benlewis.tagminigame.commands.CmdJoin;
import io.benlewis.tagminigame.commands.CmdQuit;
import io.benlewis.tagminigame.commands.CmdSetTag;
import io.benlewis.tagminigame.game.data.DataPlayerManager;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import io.benlewis.tagminigame.listeners.EntityDamageByEntityListener;
import io.benlewis.tagminigame.listeners.PlayerJoinEventListener;
import io.benlewis.tagminigame.listeners.PlayerQuitEventListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class TagPlugin extends JavaPlugin {

    private final DataPlayerManager dataPlayerManager = new DataPlayerManager();
    private final TagGameManager tagGameManager = new TagGameManager(this);
    private final boolean debug = true;

    public DataPlayerManager getPlayerDataManager() {
        return dataPlayerManager;
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

        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(dataPlayerManager, tagGameManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(dataPlayerManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(dataPlayerManager, tagGameManager), this);

        getServer().getOnlinePlayers().forEach(getPlayerDataManager()::register);
    }

    @Override
    public void onDisable(){

    }

    public void debug(String msg){
        // TODO replace with proper config/options
        if (this.debug) getLogger().info("[DEBUG] " + msg);
    }

}
