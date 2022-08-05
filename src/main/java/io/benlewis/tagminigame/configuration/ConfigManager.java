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
    private final HashMap<ConfigType, FileConfiguration> configurations;

    public ConfigManager(TagPlugin plugin) {
        this.plugin = plugin;
        configurations = new HashMap<>();
    }

    @Override
    public FileConfiguration get(ConfigType type) {
        if (configurations.containsKey(type)) {
            return configurations.get(type);
        }
        return load(type);
    }

    private FileConfiguration load(ConfigType type){
        File file = new File(plugin.getDataFolder(), type.fileName);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource(type.fileName, false);
        }

        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        }
        catch (InvalidConfigurationException e){
            plugin.getLogger().warning("Invalid configuration for file \"%s\". Using defaults.".formatted(type.fileName));
            e.printStackTrace();
        }
        catch (IOException e){
            plugin.getLogger().warning("Unable to read configuration file \"%s\". Using defaults.".formatted(type.fileName));
            e.printStackTrace();
        }
        // TODO defaults for files not loaded - this will probs be done in whatever class processes the FileConfiguration
        configurations.put(type, config);
        return config;
    }

    @Override
    public void saveAll() {
        configurations.keySet().forEach(this::save);
    }

    @Override
    public void save(ConfigType type) {
        FileConfiguration config = get(type);
        try {
            config.save(type.fileName);
            plugin.getLogger().info("Saved configuration \"%s\"".formatted(type.fileName));
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save configuration to file \"%s\"".formatted(type.fileName));
            e.printStackTrace();
        }
    }

}
