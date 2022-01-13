package io.benlewis.tagminigame.game.api;

import java.util.Collection;

public interface IGameManager <T extends IGame<? extends IPlayerWrapper, ? extends IGamePhase>> {

    /**
     * Get the {@link IGame}s stored in this manager.
     * @return the games
     */
    Collection<T> getGames();

    /**
     * True if a game with the corresponding ID exists in this manager.
     * @param id of game
     * @return true if game exists
     */
    boolean hasGame(int id);

    /**
     * Get the {@link IGame} with the given ID. Throws {@link java.util.NoSuchElementException} if no games with that ID
     * are stored in the manager.
     * @param id of game
     * @return the game
     */
    T getGame(int id);

    /**
     * Create a new {@link IGame} registered with this manager. Throws {@link IllegalArgumentException} if the generated ID is
     * already in use.
     * @return game created
     */
    T createGame(int minNoPlayers, int maxNoPlayers);

    /**
     * Delete a game, removing it from the manager (if present).
     * @param id of game to remove
     */
    void deleteGame(int id);
}
