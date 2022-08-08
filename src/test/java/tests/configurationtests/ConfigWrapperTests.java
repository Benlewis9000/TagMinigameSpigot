package tests.configurationtests;

import io.benlewis.tagminigame.configuration.ConfigWrapper;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import tests.MockBukkitTests;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigWrapperTests extends MockBukkitTests {

    @Test
    public void LoadChangeAndReloadValues(){
        ConfigWrapper wrapper = new ConfigWrapper(plugin, TestConfigType.TEST_PRESENT.getFileName());
        FileConfiguration config = wrapper.getFileConfiguration();
        assertFalse(config.getBoolean("modified"));
        config.set("modified", true);
        assertTrue(config.getBoolean("modified"));
        FileConfiguration reloadedConfig = wrapper.reload();
        assertFalse(reloadedConfig.getBoolean("modified"));
    }

    @ParameterizedTest
    @EnumSource(TestConfigType.class)
    public void SaveConfigAndCheckValue(TestConfigType configType) throws IOException, InvalidConfigurationException {
        Random rng = new Random();
        int random = rng.nextInt();
        ConfigWrapper wrapper = new ConfigWrapper(plugin, configType.getFileName());
        FileConfiguration config = wrapper.getFileConfiguration();
        config.set("random", random);
        wrapper.save();
        FileConfiguration loaded = new YamlConfiguration();
        loaded.load(new File(configType.getFileName()));
        assertEquals(random,loaded.getInt("random"));
    }

}
