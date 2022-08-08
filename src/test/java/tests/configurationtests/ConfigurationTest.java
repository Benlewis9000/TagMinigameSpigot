package tests.configurationtests;

import io.benlewis.tagminigame.configuration.ConfigManager;
import io.benlewis.tagminigame.configuration.IConfigManager;
import org.junit.jupiter.api.BeforeEach;
import tests.MockBukkitTests;

public class ConfigurationTest extends MockBukkitTests {

    protected IConfigManager configManager;

    @Override
    @BeforeEach
    protected void setUpBukkit(){
        super.setUpBukkit();
        configManager = new ConfigManager(plugin);
    }

}
