package tests.utiltests.countdowntests;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.benlewis.tagminigame.util.countdown.Countdown;
import io.benlewis.tagminigame.util.countdown.CountdownBuilder;
import org.junit.jupiter.api.Test;
import tests.MockBukkitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CountdownTests extends MockBukkitTests {

    @Test
    void Countdown_ShouldExecuteStartIntervalAndEndTasks(){
        PlayerMock p = server.addPlayer();
        CountdownBuilder cb = new CountdownBuilder(plugin, 20, 20)
                .startTask(() -> p.sendMessage("start"))
                .intervalTask((c) -> p.sendMessage("interval"))
                .endTask(() -> p.sendMessage("end"))
                .cancelTask(() -> p.sendMessage("cancel"));
        cb.start();
        server.getScheduler().performTicks(21);
        assertEquals("start", p.nextMessage());
        assertEquals("interval", p.nextMessage());
        assertEquals("end", p.nextMessage());
        assertNull(p.nextMessage());
    }

    @Test
    void Countdown_ShouldExecuteStartAndCancelTasks(){
        PlayerMock p = server.addPlayer();
        CountdownBuilder cb = new CountdownBuilder(plugin, 20, 20)
                .startTask(() -> p.sendMessage("start"))
                .endTask(() -> p.sendMessage("end"))
                .cancelTask(() -> p.sendMessage("cancel"));
        Countdown c = cb.start();
        server.getScheduler().performOneTick();
        assertEquals("start", p.nextMessage());
        server.getScheduler().performTicks(10);
        c.cancel();
        server.getScheduler().performTicks(10);
        assertEquals("cancel", p.nextMessage());
        server.getScheduler().performTicks(10);
        assertNull(p.nextMessage());
    }

}
