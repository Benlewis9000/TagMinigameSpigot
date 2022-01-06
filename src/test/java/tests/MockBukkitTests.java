package tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import io.benlewis.tagminigame.game.tag.TagPlayerManager;

public class MockBukkitTests {

    protected ServerMock server;
    protected TagPlugin plugin;
    protected TagPlayerManager playerManager;
    protected TagGameManager gameManager;

    protected void setUpBukkit(){
        server = MockBukkit.mock();
        plugin = MockBukkit.load(TagPlugin.class);
        playerManager = plugin.getTagPlayerManager();
        gameManager = plugin.getTagGameManager();
    }

    protected void tearDownBukkit(){
        MockBukkit.unmock();
    }

}
