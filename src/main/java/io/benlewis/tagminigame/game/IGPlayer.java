package io.benlewis.tagminigame.game;

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
    // TODO remove (or implement if alternatives fail) - may actually need down the line as overload incase
    //      game data is needed to help build IGPlayer
    // int getGameId();
}
