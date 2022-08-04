package io.benlewis.tagminigame.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigManager {

    /**
     * Get the {@link FileConfiguration} for a config file.
     * @param type type of config to get
     * @return {@link FileConfiguration} associated with the given file type
     */
    FileConfiguration get(ConfigType type);

}
