package io.benlewis.tagminigame.game.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IPlayerWrapper {

    // TODO should getPlayer be removed? wrapper and maps should be UUID based, what if player is not online?
    // probably not - very useful since ultimately the wrappers were a workaround to not being able to extend Player
    /**
     * Get the wrapped {@link Player}.
     * @return wrapped player
     */
    Player getPlayer();

    /**
     * Get the UUID of the wrapped {@link Player}.
     * @return the players uuid
     */
    UUID getUUID();

    /**
     * Get the ID of the {@link IGame} the player is in
     * @return id of player's game
     */
     int getGameId();

}
