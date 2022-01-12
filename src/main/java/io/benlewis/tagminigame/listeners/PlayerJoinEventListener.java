package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.game.data.DataPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinEventListener(DataPlayerManager dataPlayerManager) implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!dataPlayerManager.contains(player)) {
            dataPlayerManager.register(player);
        }
    }

}
