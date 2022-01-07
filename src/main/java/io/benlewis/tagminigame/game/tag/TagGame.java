package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.api.IGame;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TagGame implements IGame<TagPlayer, TagGamePhase> {

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
        players.add(plugin.getTagPlayerManager().createWrapper(player, this.getId()));
    }

    @Override
    public void removePlayer(TagPlayer player) {
        players.remove(player);
        plugin.getTagPlayerManager().destroyWrapper(player);
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

    public void playerQuit(TagPlayer player){
        if (players.contains(player)) {
            removePlayer(player);
            player.getPlayer().sendMessage(ChatColor.GREEN + "You have quit game " + getId() + ".");
        }
    }

    public void playerHitPlayer(EntityDamageByEntityEvent event, TagPlayer attacker, TagPlayer victim){
        if (!attacker.isTagged()) {
            plugin.debug("event cancelled");
            event.setCancelled(true);
            plugin.debug("Test debug");
            return;
        }
        plugin.debug("attacker is tagged");
        if (victim.isTagged()) {
            attacker.getPlayer().sendMessage(ChatColor.RED + victim.getPlayer().getDisplayName() +
                    " is already tagged!");
            event.setCancelled(true);
            return;
        }
        plugin.debug("victim is not tagged");
        plugin.debug("doing placeholder tag code");
        event.setDamage(0.0);
        playerTagPlayer(attacker, victim);
    }

    public void playerTagPlayer(TagPlayer attacker, TagPlayer victim){
        attacker.setTagged(false);
        attacker.getPlayer().sendMessage(ChatColor.GREEN + "You just tagged "
                + victim.getPlayer().getDisplayName() + "!");
        victim.setTagged(true);
        victim.getPlayer().sendMessage(ChatColor.RED + "You have just been tagged by "
                + attacker.getPlayer().getDisplayName() + "!");
    }

}
