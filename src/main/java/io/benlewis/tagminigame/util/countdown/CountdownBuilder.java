package io.benlewis.tagminigame.util.countdown;

import io.benlewis.tagminigame.TagPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class CountdownBuilder {

    private final TagPlugin plugin;

    private final long totalTicks;
    private final long intervalTicks;
    private Consumer<Countdown> intervalTask;
    private Runnable startTask;
    private Runnable endTask;
    private Runnable cancelTask;

    public CountdownBuilder(TagPlugin plugin, long totalTicks, long intervalTicks){
        this.plugin = plugin;
        this.totalTicks = totalTicks;
        this.intervalTicks = intervalTicks;
        this.intervalTask = (t) -> {};
        this.startTask = () -> {};
        this.endTask = () -> {};
        this.cancelTask = () -> {};
    }

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

    public CountdownBuilder intervalTask(Consumer<Countdown> intervalTask){
        this.intervalTask = intervalTask;
        return this;
    }

    public CountdownBuilder startTask(Runnable startTask){
        this.startTask = startTask;
        return this;
    }

    public CountdownBuilder endTask(Runnable endTask){
        this.endTask = endTask;
        return this;
    }

    public CountdownBuilder cancelTask(Runnable cancelTask){
        this.cancelTask = cancelTask;
        return this;
    }

}
