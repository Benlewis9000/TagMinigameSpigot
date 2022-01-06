package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.game.IGPlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class TagPlayerManager implements IGPlayerManager<TagPlayer> {

    private final HashMap<UUID, TagPlayer> players = new HashMap<>();

    @Override
    public TagPlayer createGPlayer(Player player, int gameId) {
        if (hasPlayer(player)){
            throw new IllegalArgumentException("a wrapper for Player " + player.getName() + " already exists");
        }
        TagPlayer tagPlayer = new TagPlayer(player, gameId);
        players.put(player.getUniqueId(), tagPlayer);
        return tagPlayer;
    }

    @Override
    public void destroyGPlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    @Override
    public void destroyGPlayer(TagPlayer player) {
        destroyGPlayer(player.getPlayer());
    }

    @Override
    public boolean hasPlayer(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    @Override
    public TagPlayer getGPlayer(Player player) {
        return requireNonNull(players.get(player.getUniqueId()), "a wrapper for Player " + player.getName()
                + " was not found");
    }
}
