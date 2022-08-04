package io.benlewis.tagminigame.configuration;

public enum ConfigType {
    CONFIG ("config.yml"),
    ARENAS ("arenas.yml");

    public final String fileName;

    ConfigType(String fileName) {
        this.fileName = fileName;
    }
}
