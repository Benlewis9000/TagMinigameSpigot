package io.benlewis.tagminigame.configuration;

import io.benlewis.tagminigame.TagPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager implements IConfigManager {

    private final TagPlugin plugin;
    private final HashMap<IConfigType, FileConfiguration> configurations;

    // TODO encapsulate/protect, should only be one configmanager, hard to access
    public ConfigManager(TagPlugin plugin) {
        this.plugin = plugin;
        configurations = new HashMap<>();
    }

    @Override
    public FileConfiguration get(IConfigType type) {
        if (configurations.containsKey(type)) {
            return configurations.get(type);
        }
        return reload(type);
    }

    @Override
    public FileConfiguration reload(IConfigType type){
        File file = new File(plugin.getDataFolder(), type.getFileName());
        if (!file.exists()) {
            // Attempt to copy default template of config, if one is present in jar
            file.getParentFile().mkdirs();
            try {
                plugin.saveResource(type.getFileName(), false);
            } catch(IllegalArgumentException e){
                plugin.getLogger().warning("No template file was found for \"%s\"".formatted(type.getFileName()));
            }
        }

        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        }
        catch (InvalidConfigurationException e){
            plugin.getLogger().warning(("""
                    Invalid configuration for file "%s":
                    %s
                    Using defaults.""").formatted(type.getFileName(), e.getMessage()));
        }
        catch (IOException e){
            plugin.getLogger().warning(("""
                    Unable to read configuration file "%s":
                    %s
                    Using defaults.""").formatted(type.getFileName(), e.getMessage()));
        }
        configurations.put(type, config);
        return config;
    }

    @Override
    public void saveAll() {
        configurations.keySet().forEach(this::save);
    }

    @Override
    public void save(IConfigType type) {
        FileConfiguration config = get(type);
        try {
            config.save(type.getFileName());
            plugin.getLogger().info("Saved configuration \"%s\"".formatted(type.getFileName()));
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save configuration to file \"%s\"".formatted(type.getFileName()));
            e.printStackTrace();
        }
    }

}
