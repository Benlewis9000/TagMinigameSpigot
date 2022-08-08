package io.benlewis.tagminigame.configuration;

import io.benlewis.tagminigame.TagPlugin;

import java.util.HashMap;

public class ConfigManager implements IConfigManager {

    private final TagPlugin plugin;
    private final HashMap<IConfigType, IConfigWrapper> configurations;

    public ConfigManager(TagPlugin plugin) {
        this.plugin = plugin;
        configurations = new HashMap<>();
    }

    @Override
    public IConfigWrapper get(IConfigType type) {
        if (configurations.containsKey(type)) {
            return configurations.get(type);
        }
        IConfigWrapper wrapper = new ConfigWrapper(plugin, type.getFileName());
        configurations.put(type, wrapper);
        return wrapper;
    }

    @Override
    public void saveAll() {
        configurations.values().forEach(IConfigWrapper::save);
    }

}
