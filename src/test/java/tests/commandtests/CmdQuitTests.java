package tests.commandtests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.tag.TagGame;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmdQuitTests extends MockBukkitTests {

    @Test
    void shouldQuitGameSuccessfully(){
        PlayerMock p = server.addPlayer();
        TagGame game = gameManager.createGame(2,2);
        game.register(p);
        assertTrue(game.contains(p));
        p.addAttachment(plugin).setPermission("tag.quit", true);
        plugin.getServer().dispatchCommand(p, "quit");
        assertFalse(game.contains(p));
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("you have quit"));
    }

    @Test
    void notInAGame_ShouldFail(){
        PlayerMock p = server.addPlayer();
        p.addAttachment(plugin).setPermission("tag.quit", true);
        plugin.getServer().dispatchCommand(p, "quit");
        assertTrue(p.nextMessage().toLowerCase(Locale.UK).contains("you are not in a game"));
    }

}
