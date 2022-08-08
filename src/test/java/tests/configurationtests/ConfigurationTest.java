package tests.configurationtests;

import io.benlewis.tagminigame.configuration.ConfigManager;
import org.junit.jupiter.api.BeforeEach;
import tests.MockBukkitTests;

public class ConfigurationTest extends MockBukkitTests {

    protected ConfigManager configManager;

    @Override
    @BeforeEach
    protected void setUpBukkit(){
        super.setUpBukkit();
        configManager = new ConfigManager(plugin);
    }

}
