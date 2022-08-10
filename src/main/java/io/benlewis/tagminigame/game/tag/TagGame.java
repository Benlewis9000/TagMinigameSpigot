package io.benlewis.tagminigame.game.tag;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.api.Game;
import io.benlewis.tagminigame.game.api.GamePhase;
import io.benlewis.tagminigame.game.data.DataPlayerManager;
import io.benlewis.tagminigame.util.countdown.Countdown;
import io.benlewis.tagminigame.util.countdown.CountdownBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.benlewis.tagminigame.game.tag.TagGamePhase.GAME;
import static io.benlewis.tagminigame.game.tag.TagGamePhase.LOBBY;
import static java.util.Objects.requireNonNull;

public class TagGame implements Game {

    private final TagPlugin plugin;
    private final int id;
    private final Map<UUID, TagPlayer> players;
    private final int maxPlayers;
    private final int minPlayers;
    private final int countdownTimeSeconds;
    private GamePhase phase;
    private Countdown countdown;

    protected TagGame(TagPlugin plugin, int id, int minPlayers, int maxPlayers){
        this.plugin = plugin;
        this.id = id;
        // TODO: Have these be part of some options/config
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.countdownTimeSeconds = 10;
        players = new HashMap<>();
        phase = LOBBY;
        countdown = null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    @Override
    public TagPlayer register(Player player) {
        if (getPhase() != LOBBY){
            throw new IllegalStateException("players cannot be registered outside of the LOBBY phase");
        }
        if (players.size() >= maxPlayers){
            throw new IllegalStateException("maximum player limit has already been met");
        }
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
        if (players.size() >= minPlayers){
            startCountdown();
        }
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
        if (phase == LOBBY && players.size() < minPlayers) {
            cancelCountdown();
            players.forEach((k, v) -> v.getPlayer().sendMessage(
                    ChatColor.RED + "Not enough players to start the game! Countdown cancelled."));
        }
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
    public GamePhase getPhase() {
        return this.phase;
    }

    @Override
    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    /**
     * Start the game.
     */
    public void startGame(){
        phase = GAME;
        // TODO
    }

    /**
     * Start a countdown to start the game.
     */
    // TODO take period in seconds and when to send messages as array in seconds
    private void startCountdown() {
        if (phase != LOBBY) {
            throw new IllegalStateException("tried to start lobby countdown outside of lobby");
        }
        if (countdown != null) {
            throw new IllegalStateException("tried to start lobby countdown when one already in progress");
        }
        countdown = new CountdownBuilder(plugin, countdownTimeSeconds * 20L, 20)
                .startTask(() -> players.forEach((k, v) -> v.getPlayer().sendMessage(
                        ChatColor.GREEN + "Minimum players reached! Game start in " + countdownTimeSeconds
                                + "s!"))
                )
                .intervalTask((c) -> {
                            if (c.getRemainingTicks() % (20 * 10) == 0
                                    || c.getRemainingTicks() == 20 * 5
                                    || c.getRemainingTicks() == 20 * 3
                                    || c.getRemainingTicks() == 20 * 2
                                    || c.getRemainingTicks() == 20) {
                                players.forEach((k, v) -> v.getPlayer().sendMessage(
                                        ChatColor.YELLOW + "Game starting in " + c.getRemainingTicks() / 20L
                                                + "s!"));
                            }
                        }
                )
                .endTask(this::startGame)
                .start();
    }

    /**
     * Cancel the countdown to start the game.
     */
    private void cancelCountdown(){
        if (countdown != null){
            countdown.cancel();
            countdown = null;
        }
    }

    /**
     * Logic to execute when a player in this game hits another player in this game. Zeroes damage and will call
     * {@link #transferTag(TagPlayer, TagPlayer)} if criteria met.
     * @param event where player hit player
     * @param attackerUuid attacking player's UUID
     * @param victimUuid victim player's UUID
     */
    public void playerHitPlayer(EntityDamageByEntityEvent event, UUID attackerUuid, UUID victimUuid){
        if (getPhase() != TagGamePhase.GAME) {
            event.setCancelled(true);
            return;
        }
        TagPlayer attacker = get(attackerUuid);
        TagPlayer victim = get(victimUuid);
        event.setDamage(0.0);
        if (!attacker.isTagged()) {
            return;
        }
        if (victim.isTagged()){
            attacker.getPlayer().sendMessage(ChatColor.RED + victim.getPlayer().getDisplayName() +
                    " is already tagged!");
        }
        else {
            plugin.debug("doing placeholder tag code");
            transferTag(attacker, victim);
        }
    }

    /**
     * Transfer tag from attacker to victim.
     * @param attacker tagging player
     * @param victim player to be tagged
     */
    public void transferTag(TagPlayer attacker, TagPlayer victim){
        attacker.setTagged(false);
        attacker.getPlayer().sendMessage(ChatColor.GREEN + "You just tagged "
                + victim.getPlayer().getDisplayName() + "!");
        victim.setTagged(true);
        victim.getPlayer().sendMessage(ChatColor.RED + "You have just been tagged by "
                + attacker.getPlayer().getDisplayName() + "!");
    }

}
