package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.MockBukkitTests;

import java.util.Locale;
import java.util.stream.Stream;

import static io.benlewis.tagminigame.game.tag.TagGamePhase.LOBBY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmdJoinTests extends MockBukkitTests {

    @Test
    void shouldJoinGameSuccessfully(){
        TagGame game = gameManager.createGame(2, 2);
        assertEquals(0, game.getId());
        PlayerMock p = server.addPlayer();
        p.addAttachment(plugin).setPermission("tag.join", true);
        assertFalse(game.contains(p));
        plugin.getServer().dispatchCommand(p, "join 0");
        assertTrue(game.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("joined game"));
    }

    @Test
    void notLobby_ShouldFail(){
        TagGame game = gameManager.createGame(2,2);
        game.startGame();
        assertEquals(0, game.getId());
        assertNotSame(game.getPhase(), LOBBY);
        PlayerMock p = server.addPlayer();
        p.addAttachment(plugin).setPermission("tag.join", true);
        plugin.getServer().dispatchCommand(p, "join 0");
        assertFalse(game.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("already started"));
    }

    @ParameterizedTest
    @MethodSource("provideBadArgs")
    void badArgs_ShouldWarnSender(String command, String expectedMessage){
        TagGame game = gameManager.createGame(2, 2);
        PlayerMock p = server.addPlayer();
        p.addAttachment(plugin).setPermission("tag.join", true);
        plugin.getServer().dispatchCommand(p, command);
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains(expectedMessage));
        assertFalse(game.contains(p));
    }

    private static Stream<Arguments> provideBadArgs() {
        return Stream.of(
                Arguments.of("join", "insufficient args"),
                Arguments.of("join a", "valid integer")
        );
    }

}
