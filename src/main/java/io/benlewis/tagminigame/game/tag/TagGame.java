package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.api.IGame;
import io.benlewis.tagminigame.game.data.DataPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class TagGame implements IGame<TagPlayer, TagGamePhase> {

    private final TagPlugin plugin;
    private final int id;
    private final Map<UUID, TagPlayer> players;
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
    public TagPlayer register(Player player) {
        DataPlayerManager dataPlayerManager = plugin.getPlayerDataManager();
        if (!dataPlayerManager.contains(player)){
            throw new IllegalArgumentException("there is no data wrapper for Player " + player.getName()  +
                    " [" + player.getUniqueId() + "]");
        }
        if (dataPlayerManager.get(player).isInGame()) {
            throw new IllegalArgumentException("player " + player.getName() + " is already in a game");
        }
        dataPlayerManager.get(player).setGameId(getId());
        TagPlayer tagPlayer = new TagPlayer(player, this.getId());
        players.put(player.getUniqueId(), tagPlayer);
        return tagPlayer;
    }

    @Override
    public void remove(Player player) {
        remove(player.getUniqueId());
    }

    @Override
    public void remove(UUID uuid) {
        players.remove(uuid);
        plugin.getPlayerDataManager().get(uuid).removeGameId();
    }

    @Override
    public boolean contains(Player player) {
        return contains(player.getUniqueId());
    }

    @Override
    public boolean contains(UUID uuid) {
        return players.containsKey(uuid);
    }

    @Override
    public TagPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    @Override
    public TagPlayer get(UUID uuid) {
        return requireNonNull(players.get(uuid), "a TagPlayer wrapper for UUID" + uuid
                + " was not found");
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

    public void playerHitPlayer(EntityDamageByEntityEvent event, UUID attackerUuid, UUID victimUuid){
        TagPlayer attacker = get(attackerUuid);
        TagPlayer victim = get(victimUuid);
        if (!attacker.isTagged()) {
            plugin.debug("event cancelled");
            event.setCancelled(true);
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
