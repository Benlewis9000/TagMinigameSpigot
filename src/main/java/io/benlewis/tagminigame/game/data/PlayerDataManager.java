package io.benlewis.tagminigame.game.data;

import io.benlewis.tagminigame.game.api.IPlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class PlayerDataManager implements IPlayerManager<PlayerData> {

    private final HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public PlayerData register(Player player) {
        if (contains(player)){
            throw new IllegalArgumentException("a wrapper for Player " + player.getName() + " already exists");
        }
        PlayerData tagPlayer = new PlayerData(player);
        players.put(player.getUniqueId(), tagPlayer);
        return tagPlayer;
    }

    @Override
    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    public void remove(UUID uuid){
        players.remove(uuid);
    }

    @Override
    public boolean contains(Player player) {
        return contains(player.getUniqueId());
    }

    public boolean contains(UUID uuid){
        return players.containsKey(uuid);
    }

    @Override
    public PlayerData get(Player player) {
        return get(player.getUniqueId());
    }

    public PlayerData get(UUID uuid){
        return requireNonNull(players.get(uuid), "a PlayerData wrapper for UUID" + uuid
                + " was not found");
    }
}
