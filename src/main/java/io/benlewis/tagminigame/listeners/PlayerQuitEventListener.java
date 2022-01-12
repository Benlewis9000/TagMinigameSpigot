package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.game.data.DataPlayerManager;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerQuitEventListener(DataPlayerManager dataPlayerManager, TagGameManager tagGameManager)
        implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!dataPlayerManager.contains(player)) return;
        if (!dataPlayerManager.get(player).isInGame()) return;
        int gameId = dataPlayerManager.get(player).getGameId();
        TagGame game = tagGameManager.getGame(gameId);
        game.remove(player);
    }

}
