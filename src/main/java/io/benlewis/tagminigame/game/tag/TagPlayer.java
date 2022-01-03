package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.game.IGPlayer;
import org.bukkit.entity.Player;

public class TagPlayer implements IGPlayer {

    private final Player player;
    private boolean tagged;

    protected TagPlayer(Player player){
        this.player = player;
        this.tagged = false;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Get whether the player is tagged.
     * @return true if tagged
     */
    public boolean isTagged() {
        return this.tagged;
    }

    /**
     * Set whether the player is tagged.
     * @param tagged true if tagged
     */
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

}
