package tests.configurationtests;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigManagerTests extends ConfigurationTest {

    @Test
    public void LoadPresentConfigWithValues(){
        FileConfiguration config = configManager.get(TestConfigType.TEST_PRESENT);
        assertEquals("test-present", config.getString("name"));
        assertEquals(42, config.getInt("value"));
    }

    @Test
    public void LoadChangeAndReloadValues(){
        FileConfiguration config = configManager.get(TestConfigType.TEST_PRESENT);
        assertFalse(config.getBoolean("modified"));
        config.set("modified", true);
        assertTrue(config.getBoolean("modified"));
        FileConfiguration reloadedConfig = configManager.reload(TestConfigType.TEST_PRESENT);
        assertFalse(reloadedConfig.getBoolean("modified"));
    }

    @ParameterizedTest
    @EnumSource(TestConfigType.class)
    public void SaveConfigAndCheckValue(TestConfigType configType) throws IOException, InvalidConfigurationException {
        Random rng = new Random();
        int random = rng.nextInt();
        FileConfiguration config = configManager.get(configType);
        config.set("random", random);
        configManager.save(configType);
        FileConfiguration loaded = new YamlConfiguration();
        loaded.load(new File(configType.getFileName()));
        assertEquals(random,loaded.getInt("random"));
    }

}
