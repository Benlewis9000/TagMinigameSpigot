package io.benlewis.tagminigame.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfigManager {


    public FileConfiguration get(ConfigType type);



}
