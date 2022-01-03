package gametests;

import io.benlewis.tagminigame.game.tag.TagPlayerManager;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TagPlayerManagerTests {

    @Test
    void createAndDestroyGPlayer_ShouldAddRemoveThenThrow() {
        Player p = mock(Player.class);
        TagPlayerManager manager = new TagPlayerManager();
        manager.createGPlayer(p);
        assertEquals(manager.getGPlayer(p).getPlayer(), p);
        manager.destroyGPlayer(p);
        assertThrows(NoSuchElementException.class, () -> {
            manager.getGPlayer(p);
        });
    }

}
