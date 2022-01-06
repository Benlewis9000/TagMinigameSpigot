package io.benlewis.tagminigame.events;

import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class TagPlayerQuitTagGameEvent extends TagEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final TagPlayer player;

    public TagPlayerQuitTagGameEvent(TagPlayer player) {
        this.player = player;
    }

    public TagPlayer getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
