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

    /**
     * Remove the task from the scheduler and perform any cancellation task.
     */
    public void cancel(){
        cancelTask.run();
        plugin.getServer().getScheduler().cancelTask(taskId);
    }

    /**
     * Get instance of owning plugin.
     * @return owning plugin
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Get total length of countdown in ticks.
     * @return total tick length of countdown
     */
    public long getTotalTicks() {
        return totalTicks;
    }

    /**
     * Get interval period between interval task in ticks.
     * @return interval period ticks
     */
    public long getIntervalTicks() {
        return intervalTicks;
    }

    /**
     * Get ticks remaining until task is complete.
     * @return ticks remaining
     */
    public long getRemainingTicks() {
        return remainingTicks;
    }
}
