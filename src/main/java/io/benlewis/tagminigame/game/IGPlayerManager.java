package io.benlewis.tagminigame.game;

import org.bukkit.entity.Player;

public interface IGPlayerManager<T extends IGPlayer> {

    /**
     * Create an {@link IGPlayer} for a {@link Player} registered with this manager. Throws
     * {@link IllegalArgumentException} if the {@link Player} is already registered.
     * @param player to create wrapper for
     * @return the players wrapper
     */
    T createGPlayer(Player player);

    /**
     * Remove a {@link Player} and associated {@link IGPlayer} wrapper from the manager.
     * @param player to remove from manager
     */
    void destroyGPlayer(Player player);

    /**
     * Get the {@link IGPlayer} wrapper for the given {@link Player}. Throws {@link java.util.NoSuchElementException} if
     * the {@link Player} is not registered in this manager.
     * @param player to get wrapper of
     * @return the wrapper of the player
     */
    T getGPlayer(Player player);
}
