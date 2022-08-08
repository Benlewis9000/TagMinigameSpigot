package io.benlewis.tagminigame.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigManager {

    /**
     * Get the {@link FileConfiguration} for a config file.
     * @param type type of config to get
     * @return {@link FileConfiguration} associated with the given file type
     */
    FileConfiguration get(IConfigType type);

    /**
     * Save all {@link FileConfiguration} in the manager to file.
     */
    void saveAll();

    /**
     * Save the {@link IConfigType}s associated {@link FileConfiguration} to file.
     * @param type of config to save
     */
    void save(IConfigType type);

    /**
     * Reload a {@link FileConfiguration} from file.
     * @param type type of config to get
     * @return {@link FileConfiguration} associated with the given file type with values matching those in the file
     */
    FileConfiguration reload(IConfigType type);

}
