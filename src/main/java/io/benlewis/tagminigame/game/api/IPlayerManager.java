package io.benlewis.tagminigame.game.api;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A player manager should be used by any class that requires mapping of {@link UUID}s to implementations of
 * {@link IPlayerWrapper}. Given a {@link Player}, the manager will create a new instance of the wrapper, mapped to by
 * the players UUID. The manager can then perform contains, get and remove queries on the internal map.
 */
public interface IPlayerManager {

    /**
     * Create an {@link IPlayerWrapper} for a {@link Player} registered with this manager. Throws
     * {@link IllegalArgumentException} if the player is already registered.
     * @param player to create wrapper for
     * @return the players wrapper
     */
    IPlayerWrapper register(Player player);

    /**
     * Remove a {@link Player} and associated {@link IPlayerWrapper} wrapper from the manager.
     * @param player to remove from manager
     */
    void remove(Player player);

    /**
     * Remove a {@link Player} and associated {@link IPlayerWrapper} wrapper from the manager.
     * @param uuid the player to removes UUID
     */
    void remove(UUID uuid);


    /**
     * Query whether an {@link IPlayerWrapper} wrapper exists for the given {@link Player}.
     * @param player to check for wrapper of
     * @return true if wrapper found
     */
    boolean contains(Player player);

    /**
     * Query whether an {@link IPlayerWrapper} wrapper exists for a given {@link Player}.
     * @param uuid of player to check wrapper exists for
     * @return true if wrapper found
     */
    boolean contains(UUID uuid);

    /**
     * Get the {@link IPlayerWrapper} wrapper for a given {@link Player}. Throws {@link java.util.NoSuchElementException} if
     * the players {@link UUID} is not registered in this manager.
     * @param player to get wrapper of
     * @return the wrapper of the player
     */
    IPlayerWrapper get(Player player);

    /**
     * Get the {@link IPlayerWrapper} wrapper for a given {@link Player}. Throws {@link java.util.NoSuchElementException} if
     * the players {@link UUID} is not registered in this manager.
     * @param uuid of player to get wrapper of
     * @return the wrapper of the player
     */
    IPlayerWrapper get(UUID uuid);
}
