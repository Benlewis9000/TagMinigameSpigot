package io.benlewis.tagminigame.configuration;

public enum ConfigType implements IConfigType {
    CONFIG ("config.yml"),
    ARENAS ("arenas.yml");

    private final String fileName;

    ConfigType(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
