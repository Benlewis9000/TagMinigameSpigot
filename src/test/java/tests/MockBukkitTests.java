package tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.data.DataPlayerManager;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class MockBukkitTests {

    protected ServerMock server;
    protected TagPlugin plugin;
    protected DataPlayerManager dataPlayerManager;
    protected TagGameManager gameManager;

    @BeforeEach
    protected void setUpBukkit(){
        server = MockBukkit.mock();
        plugin = MockBukkit.load(TagPlugin.class);
        dataPlayerManager = plugin.getPlayerDataManager();
        gameManager = plugin.getTagGameManager();
    }

    @AfterEach
    protected void tearDownBukkit(){
        MockBukkit.unmock();
    }

}
