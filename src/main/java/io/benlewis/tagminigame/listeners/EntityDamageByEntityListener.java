package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.game.data.DataPlayer;
import io.benlewis.tagminigame.game.data.DataPlayerManager;
import io.benlewis.tagminigame.game.tag.TagGameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public record EntityDamageByEntityListener(DataPlayerManager dataPlayerManager, TagGameManager tagGameManager)
        implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (!(damager instanceof Player pDamager) || !(entity instanceof Player pDamaged) ) return;
        DataPlayer pDataDamager = dataPlayerManager.get(pDamager);
        DataPlayer pDataDamaged = dataPlayerManager.get(pDamaged);
        if (!pDataDamager.isInGame() && !pDataDamaged.isInGame()) return;
        if (pDataDamager.isInGame() && !pDataDamaged.isInGame()) {
            pDamager.sendMessage(ChatColor.RED + "This player is not in your game.");
            event.setCancelled(true);
            return;
        }
        if (!pDataDamager.isInGame() && pDataDamaged.isInGame()) {
            pDamager.sendMessage(ChatColor.RED + "You cannot harm a player who is in a game.");
            event.setCancelled(true);
            return;
        }
        if (pDataDamager.getGameId() != pDataDamaged.getGameId()) {
            pDataDamager.getPlayer().sendMessage(ChatColor.RED + "You cannot tag someone who is not in your game!");
            event.setCancelled(true);
            return;
        }
        int gameId = pDataDamager.getGameId();
        tagGameManager.getGame(gameId).playerHitPlayer(event, pDataDamager.getUUID(), pDataDamaged.getUUID());
    }

}
