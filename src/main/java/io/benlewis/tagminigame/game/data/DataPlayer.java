package io.benlewis.tagminigame.game.data;

import io.benlewis.tagminigame.game.api.IPlayerWrapper;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class DataPlayer implements IPlayerWrapper {

    private final Server server;
    private final UUID playerUuid;
    private Optional<Integer> gameId;

    protected DataPlayer(Player player){
        this.server = player.getServer();
        this.playerUuid = player.getUniqueId();
        this.gameId = Optional.empty();
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
        if (!isInGame()){
            throw new NullPointerException("Player with UUID " + playerUuid + " is not in a game");
        }
        return gameId.get();
    }

    /**
     * Set the players game ID to that of the game they are in.
     * @param id ID of game, where id >= 0
     */
    public void setGameId(int id) {
        if (id < 0){
            throw new IllegalArgumentException("a game ID is a positive integer, it cannot be " + id);
        }
        this.gameId = Optional.of(id);
    }

    /**
     * Query whether the player is in a game.
     * @return true if player is in game
     */
    public boolean isInGame(){
        return gameId.isPresent();
    }

    /**
     * Remove the players game ID. Player will no longer be considered to be in a game.
     */
    public void removeGameId(){
        this.gameId = Optional.empty();
    }

}
