package io.benlewis.tagminigame.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigWrapper {

    /**
     * Get the wrapped {@link FileConfiguration}.
     * @return wrapped {@link FileConfiguration}
     */
    FileConfiguration getFileConfiguration();

    /**
     * Reload the wrapped {@link FileConfiguration} from file.
     * @return the wrapped {@link FileConfiguration} updated to match values in file
     */
    FileConfiguration reload();

    /**
     * Save the wrapped {@link FileConfiguration} to file.
     */
    void save();

}