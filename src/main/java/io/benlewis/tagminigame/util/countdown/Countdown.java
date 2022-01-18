package io.benlewis.tagminigame.util.countdown;

import io.benlewis.tagminigame.TagPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class Countdown implements Runnable {

    private final TagPlugin plugin;

    // All timing done in ticks (20/s) unless otherwise specified
    private final long totalTicks;
    private final long intervalTicks;
    private final Consumer<Countdown> intervalTask;
    private final Runnable startTask;
    private final Runnable endTask;
    private final Runnable cancelTask;
    private final int taskId;
    private long remainingTicks;

    protected Countdown(TagPlugin plugin, long totalTicks, long intervalTicks, Consumer<Countdown> intervalTask,
                        Runnable startTask, Runnable endTask, Runnable cancelTask) {
        this.plugin = plugin;
        this.totalTicks = totalTicks;
        this.intervalTicks = intervalTicks;
        this.intervalTask = intervalTask;
        this.startTask = startTask;
        this.endTask = endTask;
        this.cancelTask = cancelTask;
        this.remainingTicks = totalTicks;
        this.taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
                plugin, this, 0L, intervalTicks);
    }

    @Override
    public void run() {
        if (remainingTicks < 1){
            endTask.run();
            plugin.getServer().getScheduler().cancelTask(taskId);
            return;
        }
        if (remainingTicks == totalTicks){
            startTask.run();
        }
        intervalTask.accept(this);

        remainingTicks -= intervalTicks;
    }

    public void cancel(){
        cancelTask.run();
        plugin.getServer().getScheduler().cancelTask(taskId);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public long getTotalTicks() {
        return totalTicks;
    }

    public long getIntervalTicks() {
        return intervalTicks;
    }

    public long getRemainingTicks() {
        return remainingTicks;
    }
}
