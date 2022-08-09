package io.benlewis.tagminigame.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigManager {

    /**
     * Get the {@link IConfigWrapper} for a config file.
     * @param type type of config to get
     * @return {@link IConfigWrapper} associated with the given file type
     */
    IConfigWrapper get(IConfigType type);

    /**
     * Save all {@link FileConfiguration} in the manager to file.
     */
    void saveAll();

}
