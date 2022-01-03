package gametests;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({io.benlewis.tagminigame.TagPlugin.class, org.bukkit.Server.class, org.bukkit.plugin.PluginManager.class})
//@PrepareForTest(fullyQualifiedNames ={"org.bukkit.*","io.benlewis.tagminigame.TagPlugin.class"})
public class TagGameManagerTests {

    private TagPlugin plugin;
    private Server server;
    private PluginManager pm;

    @BeforeEach
    void setUp(){
        plugin = mock(TagPlugin.class);
        server = mock(Server.class);
        pm = mock(PluginManager.class);
        when(plugin.getServer()).thenReturn(server);
        when(server.getPluginManager()).thenReturn(pm);
    }

    @Test
    void createGame_ShouldIncrementGameId(){
        TagGameManager manager = new TagGameManager(plugin);
        manager.createGame();
        assertEquals(1, manager.createGame().getId());
    }

    @Test
    void addAndRemoveGame_ShouldAddRemoveThenThrow() {
        TagGameManager manager = new TagGameManager(plugin);
        TagGame game = manager.createGame();
        assertEquals(manager.getGame(game.getId()), game);
        manager.deleteGame(0);
        assertThrows(NoSuchElementException.class, () ->{
            manager.getGame(0);
        });
    }

}
