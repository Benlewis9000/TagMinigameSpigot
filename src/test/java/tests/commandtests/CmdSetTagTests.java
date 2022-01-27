package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tests.MockBukkitTests;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmdSetTagTests extends CommandTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldSetPlayerArgsTagStatusSuccessfully(boolean tagStatus){
        TagGame game = gameManager.createGame(2,2);
        game.register(p);
        game.startGame();
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, "settag " + p.getName() + " " + tagStatus);
        assertEquals(tagStatus, game.get(p).isTagged());
    }

    @Test
    void playerNotOnline_ShouldFail(){
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, "settag a true");
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("is not online"));
    }

    @Test
    void playerNotInGame_ShouldFail(){
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, "settag p true");
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("is not in a game"));
    }

    @ParameterizedTest
    @MethodSource("provideBadArgs")
    void badArgs_ShouldWarnSender(String command, String expectedMessage){
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, command);
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains(expectedMessage));
        assertFalse(gameManager.hasGame(0));
    }

    private static Stream<Arguments> provideBadArgs() {
        return Stream.of(
                // 17 char, greater than 16 char name limit but not UUID
                Arguments.of("settag a", "insufficient args"),
                Arguments.of("settag p 1", "invalid args")
        );
    }

}
