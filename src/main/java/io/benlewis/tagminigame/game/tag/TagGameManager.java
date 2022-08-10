package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.api.Game;
import io.benlewis.tagminigame.game.api.GameManager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TagGameManager implements GameManager {

    private final TagPlugin plugin;
    private final Map<Integer, TagGame> games;
    private int nextGameId;

    public TagGameManager(TagPlugin plugin) {
        this.plugin = plugin;
        this.games = new HashMap<>();
        this.nextGameId = 0;
    }

    @Override
    public Collection<Game> getGames() {
        return Collections.unmodifiableCollection(games.values());
    }

    @Override
    public boolean hasGame(int id) {
        return games.containsKey(id);
    }

    @Override
    public TagGame getGame(int id) {
        if (games.containsKey(id)) return games.get(id);
        else throw new NoSuchElementException("no game with ID of " + id + "found");
    }

    @Override
    public TagGame createGame(int minNoPlayers, int maxNoPlayers) {
        int id = nextGameId++;
        if (games.containsKey(id)){
            throw new IllegalArgumentException("ID " + id + " is already being used by another game");
        }
        TagGame game = new TagGame(plugin, id, minNoPlayers, maxNoPlayers);
        games.put(game.getId(), game);
        return game;
    }

    @Override
    public void deleteGame(int id) {
        games.remove(id);
    }
}
