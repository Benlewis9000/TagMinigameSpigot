package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.MockBukkitTests;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmdCreateTests extends MockBukkitTests {

    @Test
    void shouldCreateGameSuccessfully(){
        assertThrows(NoSuchElementException.class, () -> plugin.getTagGameManager().getGame(0));
        PlayerMock p = server.addPlayer();
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, "create 1 2");
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("game created"));
        assertTrue(gameManager.hasGame(0));
    }

    @ParameterizedTest
    @MethodSource("provideBadArgs")
    void badArgs_ShouldWarnSender(String command, String expectedMessage){
        assertThrows(NoSuchElementException.class, () -> plugin.getTagGameManager().getGame(0));
        PlayerMock p = server.addPlayer();
        p.setOp(true);
        plugin.getServer().dispatchCommand(p, command);
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains(expectedMessage));
        assertFalse(gameManager.hasGame(0));
    }

    private static Stream<Arguments> provideBadArgs() {
        return Stream.of(
                Arguments.of("create 1", "insufficient args"),
                Arguments.of("create 2 1", "min number of players may not be larger than max"),
                Arguments.of("create a 1", "please use a valid integer"),
                Arguments.of("create 1 a", "please use a valid integer")
        );
    }

}
