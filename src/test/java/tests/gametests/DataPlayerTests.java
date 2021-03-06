package tests.gametests;

import io.benlewis.tagminigame.game.data.DataPlayer;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataPlayerTests extends MockBukkitTests {

    @Test
    void gameIdTests(){
        Player p = server.addPlayer();
        DataPlayer dp = dataPlayerManager.get(p);
        assertFalse(dp.isInGame());
        dp.setGameId(0);
        assertTrue(dp.isInGame());
        assertThrows(IllegalArgumentException.class, () -> dp.setGameId(-1));
        dp.removeGameId();
        assertThrows(NullPointerException.class, dp::getGameId);
    }

}
