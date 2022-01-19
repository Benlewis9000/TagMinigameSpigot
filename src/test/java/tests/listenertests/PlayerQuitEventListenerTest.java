package tests.listenertests;

import io.benlewis.tagminigame.game.tag.TagGame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerQuitEventListenerTest extends MockBukkitTests {

    @Test
    void EventHandle_ShouldRemovePlayerFromAnyGame(){
        Player p = server.addPlayer();
        TagGame game = gameManager.createGame(Integer.MAX_VALUE, Integer.MAX_VALUE);
        game.register(p);
        assertTrue(dataPlayerManager.get(p).isInGame());
        PlayerQuitEvent event = new PlayerQuitEvent(p, "Disconnected");
        plugin.getServer().getPluginManager().callEvent(event);
        assertFalse(dataPlayerManager.get(p).isInGame());
    }

}
