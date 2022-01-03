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
     * Get the {@link IGPlayer} wrapper for a {@link Player}. Throws {@link NoSuchElementException} if {@link Player} cannot be found in the game.
     * @param player to get wrapper of
     * @return the players wrapper
     */
    T getGPlayer(Player player);

    /**
     * Add a {@link Player} to the game.
     * @param player to add
     */
    void addPlayer(Player player);

    /**
     * Remove a {@link Player} from the game (if present).
     * @param player to remove
     */
    void removePlayer(Player player);

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
