package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.events.TagPlayerHitTagPlayerEvent;
import io.benlewis.tagminigame.events.TagPlayerQuitTagGameEvent;
import io.benlewis.tagminigame.game.api.IGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class TagGame implements IGame<TagPlayer, TagGamePhase>, Listener {

    private final TagPlugin plugin;
    private final int id;
    private final Set<TagPlayer> players;
    private TagGamePhase phase;

    protected TagGame(TagPlugin plugin, int id){
        this.plugin = plugin;
        this.id = id;
        players = new HashSet<>();
        phase = TagGamePhase.LOBBY;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Collection<TagPlayer> getGPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    @Override
    public boolean hasGPlayer(TagPlayer player) {
        return players.contains(player);
    }

    @Override
    public void addPlayer(Player player) {
        if (plugin.getTagPlayerManager().hasPlayer(player)) {
            throw new IllegalArgumentException("player " + player.getName() + " is already in a game");
        }
        players.add(plugin.getTagPlayerManager().createGPlayer(player, this.getId()));
    }

    @Override
    public void removePlayer(TagPlayer player) {
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
    public void onPlayerQuitTagGameEvent(TagPlayerQuitTagGameEvent event){
        TagPlayer player = event.getPlayer();
        if (players.contains(player)) {
            removePlayer(player);
            player.getPlayer().sendMessage(ChatColor.GREEN + "You have quit game " + getId() + ".");
        }
    }

    @EventHandler
    public void onTagPlayerHitTagPlayerEvent(TagPlayerHitTagPlayerEvent event){
        plugin.getLogger().log(Level.INFO, "onTagPlayerHitTagPlayerEvent");
        if (event.getGameId() != getId()) return;
        plugin.getLogger().log(Level.INFO, "game id = " + getId());
        TagPlayer attacker = event.getAttacker();
        TagPlayer victim = event.getVictim();
        if (!attacker.isTagged()) {
            plugin.getLogger().log(Level.INFO, "event cancelled");
            event.getBukkitEvent().setCancelled(true);
            return;
        }
        plugin.getLogger().log(Level.INFO, "attacker is tagged");
        if (victim.isTagged()) {
            attacker.getPlayer().sendMessage(ChatColor.RED + victim.getPlayer().getDisplayName() +
                    " is already tagged!");
            event.getBukkitEvent().setCancelled(true);
            return;
        }
        plugin.getLogger().log(Level.INFO, "victim is not tagged");
        plugin.getLogger().log(Level.INFO, "doing placeholder tag code");

        // TODO real tag code, following is just placeholder for ingame testing
        event.getBukkitEvent().setDamage(0.0);
        attacker.setTagged(false);
        attacker.getPlayer().sendMessage(ChatColor.GREEN + "You just tagged "
                + victim.getPlayer().getDisplayName() + "!");
        victim.setTagged(true);
        victim.getPlayer().sendMessage(ChatColor.RED + "You have just been tagged by "
                + attacker.getPlayer().getDisplayName() + "!");
    }

}
