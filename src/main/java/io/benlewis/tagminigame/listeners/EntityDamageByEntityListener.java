package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.game.data.PlayerData;
import io.benlewis.tagminigame.game.tag.TagPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public record EntityDamageByEntityListener(TagPlugin plugin) implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (!(damager instanceof Player pDamager) || !(entity instanceof Player pDamaged) ) return;
        PlayerData pDataDamager = plugin.getPlayerDataManager().get(pDamager);
        PlayerData pDataDamaged = plugin.getPlayerDataManager().get(pDamaged);
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
        plugin.getTagGameManager().getGame(gameId).playerHitPlayer(event, pDataDamager.getUUID(), pDataDamaged.getUUID());
    }

}
