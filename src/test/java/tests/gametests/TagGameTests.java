package tests.gametests;

import io.benlewis.tagminigame.game.data.DataPlayer;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void gameRegisterAndRemovePlayer_ShouldSucceed(){
        Player p = server.addPlayer();
        assertTrue(dataPlayerManager.contains(p));
        TagPlayer tp = game.register(p);
        assertTrue(game.contains(p));
        assertTrue(dataPlayerManager.get(p).isInGame());
        Player presult = tp.getPlayer();
        assertEquals(presult, p);
        game.remove(p);
        assertFalse(game.contains(p));
        assertFalse(dataPlayerManager.get(p).isInGame());
    }

    @Test
    void gameRegisterPlayerWhenExists_ShouldThrow(){
        Player p = server.addPlayer();
        game.register(p);
        assertThrows(IllegalArgumentException.class, () -> game.register(p) );
    }

    @Test
    void gameJoinWhenInOtherGame_ShouldThrow(){
        Player p = server.addPlayer();
        game.register(p);
        TagGame game2 = gameManager.createGame();
        assertThrows(IllegalArgumentException.class, () -> game2.register(p));
    }

    @Test
    void gameQuit_ShouldRemovePlayerAndUpdateWrappers(){
        Player p = server.addPlayer();
        game.register(p);
        assertTrue(game.contains(p));
        game.playerQuit(p);
        assertFalse(game.contains(p));
        DataPlayer dp = dataPlayerManager.get(p);
        assertFalse(dp.isInGame());
        assertThrows(NullPointerException.class, dp::getGameId);
    }

}
