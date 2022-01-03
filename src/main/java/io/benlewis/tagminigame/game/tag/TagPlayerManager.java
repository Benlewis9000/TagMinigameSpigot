package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.game.IGPlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class TagPlayerManager implements IGPlayerManager<TagPlayer> {

    private final HashMap<Player, TagPlayer> players = new HashMap<>();

    @Override
    public TagPlayer createGPlayer(Player player) {
        if (players.containsKey(player)){
            throw new IllegalArgumentException("a wrapper for Player " + player.getName() + " already exists");
        }
        TagPlayer tagPlayer = new TagPlayer(player);
        players.put(player, tagPlayer);
        return tagPlayer;
    }

    @Override
    public void destroyGPlayer(Player player) {
        players.remove(player);
    }

    @Override
    public TagPlayer getGPlayer(Player player) {
        if (!players.containsKey(player)){
            throw new NoSuchElementException("a wrapper for Player " + player.getName() + " was not found");
        }
        return players.get(player);
    }
}
