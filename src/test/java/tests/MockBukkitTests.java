package tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.SimpleEntityMock;
import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.data.DataPlayerManager;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import io.benlewis.tagminigame.util.countdown.Countdown;
import io.benlewis.tagminigame.util.countdown.CountdownBuilder;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
