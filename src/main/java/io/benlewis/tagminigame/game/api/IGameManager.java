package io.benlewis.tagminigame.game.api;

import java.util.Collection;

public interface IGameManager <T extends IGame<? extends IGPlayer, ? extends IGamePhase>> {

    /**
     * Get the {@link IGame}s stored in the manager.
     * @return the games
     */
    Collection<T> getGames();

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
    T createGame();

    /**
     * Delete a game, removing it from the manager (if present).
     * @param id of game to remove
     */
    void deleteGame(int id);
}
