package tests.listenertests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// NB: Listener is required to check that EntityDamageByEntityListenerTest calls TagPlayerHitTagPlayerEvent under the
// right conditions. It is NOT to listen to EntityDamageByEntityListenerTest events. These events are constructed here.
public class EntityDamageByEntityListenerTest extends MockBukkitTests {

    @BeforeEach
    void setUp(){
        setUpBukkit();
    }

    @AfterEach
    void tearDown(){
        tearDownBukkit();
    }

    // TODO will break when game phases introduced
    @Test
    public void EventHandle_ShouldTagVictim(){
        PlayerMock attacker = server.addPlayer();
        PlayerMock victim = server.addPlayer();
        TagGame game = plugin.getTagGameManager().createGame(Integer.MAX_VALUE, Integer.MAX_VALUE);
        game.register(attacker);
        game.register(victim);
        TagPlayer tpAttacker = game.get(attacker);
        TagPlayer tpVictim = game.get(victim);
        tpAttacker.setTagged(true);
        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(attacker, victim,
                EntityDamageEvent.DamageCause.CUSTOM, 0.0);
        server.getPluginManager().callEvent(event);
        assertFalse(tpAttacker.isTagged());
        assertTrue(tpVictim.isTagged());
    }

}
