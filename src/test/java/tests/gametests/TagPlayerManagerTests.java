package tests.gametests;

import io.benlewis.tagminigame.game.tag.TagPlayerManager;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TagPlayerManagerTests extends MockBukkitTests {

    @BeforeEach
    void setUp(){
        setUpBukkit();
    }

    @AfterEach
    void tearDown(){
        tearDownBukkit();
    }

    @Test
    void createAndDestroyGPlayer_ShouldAddRemoveThenThrow() {
        Player p = server.addPlayer();
        playerManager.createGPlayer(p, 0);
        assertEquals(playerManager.getGPlayer(p).getPlayer(), p);
        playerManager.destroyGPlayer(p);
        assertThrows(NullPointerException.class, () -> playerManager.getGPlayer(p) );
    }

}
