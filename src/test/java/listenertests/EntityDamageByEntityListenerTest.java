package listenertests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.events.TagPlayerHitTagPlayerEvent;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// NB: Listener is required to check that EntityDamageByEntityListenerTest calls TagPlayerHitTagPlayerEvent under the
// right conditions. It is NOT to listen to EntityDamageByEntityListenerTest events. These events are constructed here.
public class EntityDamageByEntityListenerTest implements Listener {

    private ServerMock server;
    private TagPlugin plugin;
    private TagPlayerHitTagPlayerEvent tagEvent = null;

    @EventHandler
    public void getEvent(TagPlayerHitTagPlayerEvent tagEvent){
        this.tagEvent = tagEvent;
    }

    @BeforeEach
    public void setUp(){
        server = MockBukkit.mock();
        plugin = MockBukkit.load(TagPlugin.class);
        server.getPluginManager().registerEvents(this, plugin);
    }

    @AfterEach
    public void tearDown(){
        MockBukkit.unmock();
    }

    @Test
    public void EventHandle_ShouldThrowTagPlayerHitTagPlayerEvent(){
        PlayerMock attacker = server.addPlayer();
        PlayerMock victim = server.addPlayer();
        TagGame game = plugin.getTagGameManager().createGame();
        game.addPlayer(attacker);
        game.addPlayer(victim);
        TagPlayer tpAttacker = plugin.getTagPlayerManager().getGPlayer(attacker);
        TagPlayer tpVictim = plugin.getTagPlayerManager().getGPlayer(victim);
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, victim, EntityDamageEvent.DamageCause.CUSTOM, 0.0);
        server.getPluginManager().callEvent(event);
        assertEquals(tpAttacker, tagEvent.getAttacker());
        assertEquals(tpVictim, tagEvent.getVictim());
    }

}
