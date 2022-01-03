package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.IGame;
import io.benlewis.tagminigame.game.IGameManager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TagGameManager implements IGameManager {

    private final TagPlugin plugin;
    private Map<Integer, TagGame> games;
    private int nextGameId;

    public TagGameManager(TagPlugin plugin) {
        this.plugin = plugin;
        this.games = new HashMap<>();
        this.nextGameId = 0;
    }

    @Override
    public Collection<IGame> getGames() {
        return Collections.unmodifiableCollection(games.values());
    }

    @Override
    public TagGame getGame(int id) {
        if (games.containsKey(id)) return games.get(id);
        else throw new NoSuchElementException("no game with ID of " + id + "found");
    }

    @Override
    public TagGame createGame() {
        int id = nextGameId++;
        if (games.containsKey(id)){
            throw new IllegalArgumentException("ID " + id + " is already being used by another game");
        }
        TagGame game = new TagGame(plugin, id);
        games.put(game.getId(), game);
        plugin.getServer().getPluginManager().registerEvents(game, plugin);
        return game;
    }

    @Override
    public void deleteGame(int id) {
        if (games.containsKey(id)) {
            games.get(id).close();
            games.remove(id);
        }
    }
}
