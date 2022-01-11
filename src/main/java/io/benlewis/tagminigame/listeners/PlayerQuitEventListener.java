package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerQuitEventListener(TagPlugin plugin) implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getPlayerDataManager().contains(player)) return;
        if (!plugin.getPlayerDataManager().get(player).isInGame()) return;
        int gameId = plugin.getPlayerDataManager().get(player).getGameId();
        TagGame game = plugin.getTagGameManager().getGame(gameId);
        game.playerQuit(player);
    }

}
