package io.benlewis.tagminigame.game;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface IGame<T extends IGPlayer, V extends IGamePhase> {

    /**
     * Get the ID of the game.
     * @return game ID
     */
    int getId();

    /**
     * Get the {@link IGPlayer} in the game.
     * @return {@link IGPlayer}s in game
     */
    Collection<T> getGPlayers();

    /**
     * Query whether a {@link T} is in the game.
     * @param player to check for
     * @return true if player is the game
     */
    boolean hasGPlayer(T player);

    /**
     * Add a {@link Player} to the game.
     * @param player to add
     */
    void addPlayer(Player player);

    /**
     * Remove an {@link IGPlayer} from the game (if present).
     * @param player to remove
     */
    void removePlayer(T player);

    /**
     * Get the current {@link IGamePhase} the game is in.
     * @return current game phase
     */
    V getPhase();

    /**
     * Set the current {@link IGamePhase} of the game.
     * @param phase to set game to
     */
    void setPhase(V phase);

    /**
     * Close a game.
     */
    void close();
}
