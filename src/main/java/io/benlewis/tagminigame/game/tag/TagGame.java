package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.events.PlayerQuitTagGameEvent;
import io.benlewis.tagminigame.game.IGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TagGame implements IGame<TagPlayer, TagGamePhase>, Listener {

    private final TagPlugin plugin;
    private final int id;
    private final Map<Player, TagPlayer> players;
    private TagGamePhase phase;

    protected TagGame(TagPlugin plugin, int id){
        this.plugin = plugin;
        this.id = id;
        players = new HashMap<>();
        phase = TagGamePhase.LOBBY;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Collection<TagPlayer> getGPlayers() {
        return Collections.unmodifiableCollection(players.values());
    }

    @Override
    public TagPlayer getGPlayer(Player player) {
        if (players.containsKey(player)){
            return players.get(player);
        }
        else {
            throw new NoSuchElementException("no player " + player.getName() + " found in this game");
        }
    }

    @Override
    public void addPlayer(Player player) {
        if (!players.containsKey(player)) {
            players.put(player, plugin.getTagPlayerManager().createGPlayer(player));
        }
        else {
            throw new IllegalArgumentException("player " + player.getName() + " is already in this game");
        }
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
        plugin.getTagPlayerManager().destroyGPlayer(player);
    }

    @Override
    public TagGamePhase getPhase() {
        return this.phase;
    }

    @Override
    public void setPhase(TagGamePhase phase) {
        this.phase = phase;
    }

    public void start(){
        // TODO
    }

    @Override
    public void close() {
        // TODO
    }

    @EventHandler
    public void onPlayerQuitTagGameEvent(PlayerQuitTagGameEvent event){
        if (!event.getHandled()) {
            Player player = event.getPlayer();
            if (this.players.containsKey(player)) {
                removePlayer(player);
                player.sendMessage(ChatColor.GREEN + "You have quit game " + getId() + ".");
                event.setHandled();
            }
        }
    }

}
