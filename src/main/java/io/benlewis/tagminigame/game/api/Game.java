package io.benlewis.tagminigame.game.api;

public interface Game extends PlayerManager {

    /**
     * Get the ID of the game.
     * @return game ID
     */
    int getId();

    /**
     * The minimum number of players required for a game to begin.
     * @return min players to begin
     */
    int getMinPlayers();

    /**
     * The maximum number of players that can be in a game.
     * @return max players
     */
    int getMaxPlayers();

    /**
     * True if the max player limit has been met.
     * @return true if game is full
     */
    boolean isFull();

    /**
     * Get the current {@link GamePhase} the game is in.
     * @return current game phase
     */
    GamePhase getPhase();

    /**
     * Set the current {@link GamePhase} of the game.
     * @param phase to set game to
     */
    void setPhase(GamePhase phase);

}
