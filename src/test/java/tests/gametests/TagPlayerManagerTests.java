package tests.gametests;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        playerManager.createWrapper(p, 0);
        assertEquals(playerManager.getWrapper(p).getPlayer(), p);
        playerManager.destroyWrapper(p);
        assertThrows(NullPointerException.class, () -> playerManager.getWrapper(p) );
    }

}
