package tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.data.PlayerDataManager;
import io.benlewis.tagminigame.game.tag.TagGameManager;

public class MockBukkitTests {

    protected ServerMock server;
    protected TagPlugin plugin;
    protected PlayerDataManager playerDataManager;
    protected TagGameManager gameManager;

    protected void setUpBukkit(){
        server = MockBukkit.mock();
        plugin = MockBukkit.load(TagPlugin.class);
        playerDataManager = plugin.getPlayerDataManager();
        gameManager = plugin.getTagGameManager();
    }

    protected void tearDownBukkit(){
        MockBukkit.unmock();
    }

}
