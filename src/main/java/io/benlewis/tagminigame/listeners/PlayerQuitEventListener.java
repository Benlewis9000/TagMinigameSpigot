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
        if (!plugin.getTagPlayerManager().hasPlayer(player)) return;
        TagPlayer tagPlayer = plugin.getTagPlayerManager().getWrapper(player);
        TagGame game = plugin.getTagGameManager().getGame(tagPlayer.getGameId());
        game.playerQuit(tagPlayer);
    }

}
