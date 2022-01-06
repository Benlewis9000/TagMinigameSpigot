package tests.gametests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import io.benlewis.tagminigame.game.tag.TagPlayerManager;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TagGameTests extends MockBukkitTests {

    private TagGame game;

    @BeforeEach
    void setUp(){
        setUpBukkit();
        game = gameManager.createGame();
    }

    @AfterEach
    public void tearDown(){
        tearDownBukkit();
    }

    @Test
    void gameAddAndRemove_ShouldSucceed(){
        Player p = server.addPlayer();
        game.addPlayer(p);
        TagPlayer tp = playerManager.getGPlayer(p);
        assertTrue(game.hasGPlayer(tp));
        assertTrue(playerManager.hasPlayer(p));
        Player presult = tp.getPlayer();
        assertEquals(presult, p);
        game.removePlayer(tp);
        assertFalse(game.hasGPlayer(tp));
        assertFalse(playerManager.hasPlayer(p));
    }

    @Test
    void gameAddWhenExists_ShouldThrow(){
        Player p = server.addPlayer();
        game.addPlayer(p);
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer(p) );
    }

}
