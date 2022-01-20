package io.benlewis.tagminigame.game.data;

import io.benlewis.tagminigame.game.api.IPlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class DataPlayerManager implements IPlayerManager<DataPlayer> {

    private final HashMap<UUID, DataPlayer> players = new HashMap<>();

    @Override
    public DataPlayer register(Player player) {
        if (contains(player)){
            throw new IllegalArgumentException("a wrapper for Player " + player.getName() + " already exists");
        }
        DataPlayer tagPlayer = new DataPlayer(player);
        players.put(player.getUniqueId(), tagPlayer);
        return tagPlayer;
    }

    @Override
    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    @Override
    public void remove(UUID uuid){
        players.remove(uuid);
    }

    @Override
    public boolean contains(Player player) {
        return contains(player.getUniqueId());
    }

    @Override
    public boolean contains(UUID uuid){
        return players.containsKey(uuid);
    }

    @Override
    public DataPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    @Override
    public DataPlayer get(UUID uuid){
        return requireNonNull(players.get(uuid), "a PlayerData wrapper for UUID" + uuid
                + " was not found");
    }
}
