package io.benlewis.tagminigame.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigManager {

    /**
     * Get the {@link FileConfiguration} for a config file.
     * @param type type of config to get
     * @return {@link FileConfiguration} associated with the given file type
     */
    FileConfiguration get(ConfigType type);

    /**
     * Save all {@link FileConfiguration} in the manager to file.
     */
    void saveAll();

    /**
     * Save the {@link FileConfiguration} associated with the type to file.
     * @param type of config to save
     */
    void save(ConfigType type);

}
