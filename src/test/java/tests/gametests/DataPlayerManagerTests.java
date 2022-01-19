package tests.gametests;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataPlayerManagerTests extends MockBukkitTests {

    @Test
    void registerAndRemove_ShouldAddRemoveThenThrow() {
        // Implicitly registers player through PlayerJoinEventListener
        Player p = server.addPlayer();
        assertEquals(dataPlayerManager.get(p).getPlayer(), p);
        dataPlayerManager.remove(p);
        assertThrows(NullPointerException.class, () -> dataPlayerManager.get(p) );
    }

}
