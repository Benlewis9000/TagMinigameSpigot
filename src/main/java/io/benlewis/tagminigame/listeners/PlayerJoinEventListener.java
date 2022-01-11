package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.game.data.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinEventListener(PlayerDataManager playerDataManager) implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!playerDataManager.contains(player)) {
            playerDataManager.register(player);
        }
    }

}
