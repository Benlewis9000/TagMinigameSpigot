package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.junit.jupiter.api.BeforeEach;
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

    PlayerMock p;

    @Override
    @BeforeEach
    protected void setUpBukkit(){
        super.setUpBukkit();
        p = server.addPlayer();
    }


    @Test
    void shouldJoinGameSuccessfully(){
        TagGame game = gameManager.createGame(2, 2);
        assertEquals(0, game.getId());
        p.addAttachment(plugin).setPermission("tag.join", true);
        assertFalse(game.contains(p));
        plugin.getServer().dispatchCommand(p, "join 0");
        assertTrue(game.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("joined game"));
    }

    @Test
    void alreadyInAGame_ShouldFail(){
        TagGame game0 = gameManager.createGame(2,2);
        TagGame game1 = gameManager.createGame(2,2);
        game0.register(p);
        p.addAttachment(plugin).setPermission("tag.join", true);
        assertTrue(game0.contains(p));
        assertEquals(game1.getId(), 1);
        plugin.getServer().dispatchCommand(p, "join 1");
        assertFalse(game1.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("already in a game"));
    }

    @Test
    void gameIsFull_ShouldFail(){
        TagGame game = gameManager.createGame(0,0);
        assertEquals(0, game.getId());
        p.addAttachment(plugin).setPermission("tag.join", true);
        plugin.getServer().dispatchCommand(p, "join 0");
        assertFalse(game.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("game is full"));
    }

    @Test
    void notLobby_ShouldFail(){
        TagGame game = gameManager.createGame(2,2);
        game.startGame();
        assertEquals(0, game.getId());
        assertNotSame(game.getPhase(), LOBBY);
        p.addAttachment(plugin).setPermission("tag.join", true);
        plugin.getServer().dispatchCommand(p, "join 0");
        assertFalse(game.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("already started"));
    }

    @ParameterizedTest
    @MethodSource("provideBadArgs")
    void badArgs_ShouldWarnSender(String command, String expectedMessage){
        TagGame game = gameManager.createGame(2, 2);
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
