package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.MockBukkitTests;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PermissionsTests extends MockBukkitTests {

    // TODO: Permission defaults unintentionally ignored here - perhaps a MockBukkit bug?
    @ParameterizedTest
    @MethodSource("permissions")
    void permissions_noPermThenPerm(String command, String permission){
        PlayerMock p = server.addPlayer();
        server.dispatchCommand(p, command);
        assertTrue(p.nextMessage().contains("do not have permission"));
        p.addAttachment(plugin).setPermission(permission, true);
        server.dispatchCommand(p, command);
        assertFalse(p.nextMessage().toLowerCase(Locale.UK).contains("do not have permission"));
    }

    private static Stream<Arguments> permissions() {
        return Stream.of(
                Arguments.of("create", "tag.create"),
                Arguments.of("join", "tag.join"),
                Arguments.of("quit", "tag.quit"),
                Arguments.of("settag", "tag.settag")
        );
    }

}
