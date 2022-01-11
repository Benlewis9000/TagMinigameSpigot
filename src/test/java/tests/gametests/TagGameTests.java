package tests.gametests;

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
    void gameAddAndRemove_ShouldSucceed(){
        Player p = server.addPlayer();
        assertTrue(playerDataManager.contains(p));
        TagPlayer tp = game.register(p);
        assertTrue(game.contains(p));
        assertTrue(playerDataManager.get(p).isInGame());
        Player presult = tp.getPlayer();
        assertEquals(presult, p);
        game.remove(p);
        assertFalse(game.contains(p));
        assertFalse(playerDataManager.get(p).isInGame());
    }

    @Test
    void gameAddWhenExists_ShouldThrow(){
        Player p = server.addPlayer();
        game.register(p);
        assertThrows(IllegalArgumentException.class, () -> game.register(p) );
    }

}
