package io.benlewis.tagminigame.game.api;

import java.util.Collection;

public interface GameManager {

    /**
     * Get the {@link Game}s stored in this manager.
     * @return the games
     */
    Collection<Game> getGames();

    /**
     * True if a game with the corresponding ID exists in this manager.
     * @param id of game
     * @return true if game exists
     */
    boolean hasGame(int id);

    /**
     * Get the {@link Game} with the given ID. Throws {@link java.util.NoSuchElementException} if no games with that ID
     * are stored in the manager.
     * @param id of game
     * @return the game
     */
    Game getGame(int id);

    /**
     * Create a new {@link Game} registered with this manager. Throws {@link IllegalArgumentException} if the generated ID is
     * already in use.
     * @param minNoPlayers minimum number of players required for a game to begin
     * @param maxNoPlayers maximum number of players that can be in the game
     * @return game created
     */
    Game createGame(int minNoPlayers, int maxNoPlayers);

    /**
     * Delete a game, removing it from the manager (if present).
     * @param id of game to remove
     */
    void deleteGame(int id);
}
