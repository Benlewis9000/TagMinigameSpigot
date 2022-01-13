package io.benlewis.tagminigame.game.api;

public interface IGame<T extends IPlayerWrapper, V extends IGamePhase> extends IPlayerManager<T>{

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
     * Get the current {@link IGamePhase} the game is in.
     * @return current game phase
     */
    V getPhase();

    /**
     * Set the current {@link IGamePhase} of the game.
     * @param phase to set game to
     */
    void setPhase(V phase);

}
