package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.tag.TagGame;
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

public class CmdSetTagTests extends MockBukkitTests {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldSetPlayerArgsTagStatusSuccessfully(boolean tagStatus){
        TagGame game = gameManager.createGame(2,2);
        PlayerMock p = server.addPlayer();
        p.setOp(true);
        game.register(p);
        game.startGame();
        plugin.getServer().dispatchCommand(p, "settag " + p.getPlayer().getName() + " " + tagStatus);
        assertEquals(tagStatus, game.get(p).isTagged());
    }

    @ParameterizedTest
    @MethodSource("provideBadArgs")
    void badArgs_ShouldWarnSender(String command, String expectedMessage){
        PlayerMock p = server.addPlayer();
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, command);
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains(expectedMessage));
        assertFalse(gameManager.hasGame(0));
    }

    private static Stream<Arguments> provideBadArgs() {
        return Stream.of(
                Arguments.of("settag", "insufficient args")
        );
    }

}
