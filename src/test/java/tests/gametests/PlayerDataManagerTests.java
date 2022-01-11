package tests.gametests;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerDataManagerTests extends MockBukkitTests {

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
        playerDataManager.register(p);
        assertEquals(playerDataManager.get(p).getPlayer(), p);
        playerDataManager.remove(p);
        assertThrows(NullPointerException.class, () -> playerDataManager.get(p) );
    }

}
