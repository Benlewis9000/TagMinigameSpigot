package io.benlewis.tagminigame.util.countdown;

import io.benlewis.tagminigame.TagPlugin;

import java.util.function.Consumer;

public class CountdownBuilder {

    private final TagPlugin plugin;

    private final long totalTicks;
    private final long intervalTicks;
    private Consumer<Countdown> intervalTask;
    private Runnable startTask;
    private Runnable endTask;
    private Runnable cancelTask;

    /**
     * Construct a CountdownBuilder.
     * @param plugin plugin
     * @param totalTicks total length of countdown in ticks
     * @param intervalTicks length of interval in ticks
     */
    public CountdownBuilder(TagPlugin plugin, long totalTicks, long intervalTicks){
        this.plugin = plugin;
        this.totalTicks = totalTicks;
        this.intervalTicks = intervalTicks;
        this.intervalTask = (t) -> {};
        this.startTask = () -> {};
        this.endTask = () -> {};
        this.cancelTask = () -> {};
    }

    /**
     * Build the countdown and immediately execute.
     * @return countdown built
     */
    public Countdown start(){
        return new Countdown(
                plugin,
                totalTicks,
                intervalTicks,
                intervalTask,
                startTask,
                endTask,
                cancelTask
        );
    }

    /**
     * Set a task to perform each interval.
     * @param intervalTask task to execute each interval
     * @return the builder instance
     */
    public CountdownBuilder intervalTask(Consumer<Countdown> intervalTask){
        this.intervalTask = intervalTask;
        return this;
    }

    /**
     * Set a task to perform at the start of the countdown.
     * @param startTask task to execute at start of countdown
     * @return builder instance
     */
    public CountdownBuilder startTask(Runnable startTask){
        this.startTask = startTask;
        return this;
    }

    /**
     * Set a task to perform at the end of the countdown.
     * @param endTask task to execute at end of countdown
     * @return builder instance
     */
    public CountdownBuilder endTask(Runnable endTask){
        this.endTask = endTask;
        return this;
    }

    /**
     * Set a task to perform when the countdown is cancelled.
     * @param cancelTask task to execute when countdown cancelled
     * @return builder instance
     */
    public CountdownBuilder cancelTask(Runnable cancelTask){
        this.cancelTask = cancelTask;
        return this;
    }

}
