package io.benlewis.tagminigame.events;

import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TagPlayerHitTagPlayerEvent extends TagEvent {

    private final EntityDamageByEntityEvent bukkitEvent;
    private final TagPlayer attacker;
    private final TagPlayer victim;
    private final int gameId;

    public TagPlayerHitTagPlayerEvent(EntityDamageByEntityEvent bukkitEvent, TagPlayer attacker, TagPlayer victim, int gameId) {
        this.bukkitEvent = bukkitEvent;
        this.attacker = attacker;
        this.victim = victim;
        this.gameId = gameId;
    }

    public EntityDamageByEntityEvent getBukkitEvent(){
        return bukkitEvent;
    }

    public TagPlayer getAttacker(){
        return this.attacker;
    }

    public TagPlayer getVictim() {
        return victim;
    }

    public int getGameId(){
        return gameId;
    }

}
