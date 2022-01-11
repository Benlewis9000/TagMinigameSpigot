package io.benlewis.tagminigame.game.api;

public interface IGame<T extends IPlayerWrapper, V extends IGamePhase> extends IPlayerManager<T>{

    /**
     * Get the ID of the game.
     * @return game ID
     */
    int getId();

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
