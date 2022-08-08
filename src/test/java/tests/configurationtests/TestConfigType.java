package tests.configurationtests;

import io.benlewis.tagminigame.configuration.IConfigType;

public enum TestConfigType implements IConfigType {
    TEST_PRESENT("test-present.yml"),
    TEST_NOT_PRESENT("test-not-present.yml");

    private final String fileName;

    TestConfigType(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

}
