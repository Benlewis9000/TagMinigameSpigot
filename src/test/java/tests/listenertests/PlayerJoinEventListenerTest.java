package tests.listenertests;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerJoinEventListenerTest extends MockBukkitTests {

    @Test
    void EventHandle_ShouldAddPlayerToDataPlayerManager(){
        Player p = server.addPlayer();
        assertTrue(dataPlayerManager.contains(p));
    }

}
