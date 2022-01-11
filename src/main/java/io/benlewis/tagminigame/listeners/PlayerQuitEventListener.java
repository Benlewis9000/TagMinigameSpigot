package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.game.data.PlayerDataManager;
import io.benlewis.tagminigame.game.tag.TagGame;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerQuitEventListener(PlayerDataManager playerDataManager, TagGameManager tagGameManager)
        implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!playerDataManager.contains(player)) return;
        if (!playerDataManager.get(player).isInGame()) return;
        int gameId = playerDataManager.get(player).getGameId();
        TagGame game = tagGameManager.getGame(gameId);
        game.playerQuit(player);
    }

}
