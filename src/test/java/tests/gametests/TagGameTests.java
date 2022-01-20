package tests.gametests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.data.DataPlayer;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import java.util.Locale;

import static io.benlewis.tagminigame.game.tag.TagGamePhase.GAME;
import static io.benlewis.tagminigame.game.tag.TagGamePhase.LOBBY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagGameTests extends MockBukkitTests {

    @Test
    void gameRegisterAndRemovePlayer_ShouldSucceed(){
        TagGame game = gameManager.createGame(Integer.MAX_VALUE, Integer.MAX_VALUE);
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
        TagGame game = gameManager.createGame(Integer.MAX_VALUE, Integer.MAX_VALUE);
        game.register(p);
        assertThrows(IllegalArgumentException.class, () -> game.register(p) );
    }

    @Test
    void gameRegisterWhenInOtherGame_ShouldThrow(){
        Player p = server.addPlayer();
        TagGame game = gameManager.createGame(Integer.MAX_VALUE, Integer.MAX_VALUE);
        game.register(p);
        TagGame game2 = gameManager.createGame(1,1);
        assertThrows(IllegalArgumentException.class, () -> game2.register(p));
    }

    @Test
    void gameRegisterWhenFull_ShouldThrow(){
        TagGame game = gameManager.createGame(1, 1);
        game.register(server.addPlayer());
        assertThrows(IllegalStateException.class, () -> game.register(server.addPlayer()));
    }

    @Test
    void gameQuit_ShouldRemovePlayerAndUpdateWrappers(){
        Player p = server.addPlayer();
        TagGame game = gameManager.createGame(Integer.MAX_VALUE, Integer.MAX_VALUE);
        game.register(p);
        assertTrue(game.contains(p));
        game.remove(p);
        assertFalse(game.contains(p));
        DataPlayer dp = dataPlayerManager.get(p);
        assertFalse(dp.isInGame());
        assertThrows(NullPointerException.class, dp::getGameId);
    }

    @Test
    void countdown_ShouldStartAndGoToGameSuccesfully(){
        PlayerMock p = server.addPlayer();
        TagGame game = gameManager.createGame(1, 1);
        game.register(p);
        // TODO: will fail when countdown is > 10s (200 ticks)
        server.getScheduler().performTicks(201);
        assertEquals(GAME, game.getPhase());
    }

    @Test
    void countdown_ShouldStartCountdownThenCancel(){
        PlayerMock pStays = server.addPlayer();
        PlayerMock pQuits = server.addPlayer();
        TagGame game = gameManager.createGame(2,2);
        game.register(pStays);
        game.register(pQuits);
        server.getScheduler().performOneTick();
        game.remove(pQuits);
        server.getScheduler().performOneTick();
        assertEquals(LOBBY, game.getPhase());
        // Skip unrelated messages the player may have received
        String nextMessage = pStays.nextMessage();
        String lastMessage = nextMessage;
        while (nextMessage != null) {
            lastMessage = nextMessage;
            nextMessage = pStays.nextMessage();
        }
        assertTrue(StringUtils.contains(lastMessage.toLowerCase(Locale.ENGLISH), "countdown cancelled"));
    }

}
