package gametests;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import io.benlewis.tagminigame.game.tag.TagPlayerManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TagGameTests {

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
    void addAndRemovePlayer_ShouldAddRemoveThenThrow(){
        Player p = mock(Player.class);
        TagPlayerManager playerManager = new TagPlayerManager();
        when(plugin.getTagPlayerManager()).thenReturn(playerManager);

        TagGameManager gameManager = new TagGameManager(plugin);
        TagGame game = gameManager.createGame();
        game.addPlayer(p);
        Player presult = game.getGPlayer(p).getPlayer();
        assertEquals(presult, p);
        game.removePlayer(p);
        assertThrows(NoSuchElementException.class, () ->{
            game.getGPlayer(p);
        });
    }

}
