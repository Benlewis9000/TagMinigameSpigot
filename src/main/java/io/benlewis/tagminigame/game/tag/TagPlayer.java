package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.game.api.IPlayerWrapper;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class TagPlayer implements IPlayerWrapper {

    private final Server server;
    private final UUID playerUuid;
    private final int gameId;
    private boolean tagged;

    protected TagPlayer(Player player, int gameId){
        this.server = player.getServer();
        this.playerUuid = player.getUniqueId();
        this.gameId = gameId;
        this.tagged = false;
    }

    @Override
    public Player getPlayer() {
        return requireNonNull(server.getPlayer(playerUuid), "no Player with UUID " + playerUuid + " is online");
    }

    @Override
    public UUID getUUID() {
        return playerUuid;
    }

    @Override
    public int getGameId() {
        return this.gameId;
    }

    /**
     * Get whether the player is tagged.
     * @return true if tagged
     */
    public boolean isTagged() {
        return this.tagged;
    }

    /**
     * Set whether the player is tagged.
     * @param tagged true if tagged
     */
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

}
