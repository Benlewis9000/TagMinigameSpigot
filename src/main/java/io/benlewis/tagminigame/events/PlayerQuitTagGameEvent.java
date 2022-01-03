package io.benlewis.tagminigame.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerQuitTagGameEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private boolean handled;

    public PlayerQuitTagGameEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getHandled(){
        return this.handled;
    }

    public void setHandled(){
        this.handled = true;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
