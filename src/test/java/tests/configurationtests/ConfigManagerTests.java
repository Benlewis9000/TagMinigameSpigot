package tests.configurationtests;

import io.benlewis.tagminigame.configuration.ConfigManager;
import io.benlewis.tagminigame.configuration.IConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigManagerTests extends MockBukkitTests {

    protected IConfigManager configManager;

    @Override
    @BeforeEach
    protected void setUpBukkit(){
        super.setUpBukkit();
        configManager = new ConfigManager(plugin);
    }

    @Test
    public void loadPresentConfigWithValues() {
        FileConfiguration config = configManager.get(TestConfigType.TEST_PRESENT).getFileConfiguration();
        assertEquals("test-present", config.getString("name"));
        assertEquals(42, config.getInt("value"));
    }

}
