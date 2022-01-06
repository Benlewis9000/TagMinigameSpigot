package io.benlewis.tagminigame.game.api;

import org.bukkit.entity.Player;

public interface IGPlayer {

    /**
     * Get the wrapped {@link Player}.
     * @return wrapped player
     */
    Player getPlayer();

    /**
     * Get the ID of the {@link IGame} the player is in
     * @return id of player's game
     */
     int getGameId();

}
