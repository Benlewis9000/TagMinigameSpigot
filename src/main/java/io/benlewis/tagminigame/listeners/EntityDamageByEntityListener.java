package io.benlewis.tagminigame.listeners;

import io.benlewis.tagminigame.TagPlugin;
import io.benlewis.tagminigame.events.TagPlayerHitTagPlayerEvent;
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
        if (!plugin.getTagPlayerManager().hasPlayer(pDamager) && !plugin.getTagPlayerManager().hasPlayer(pDamaged)) return;
        if (plugin.getTagPlayerManager().hasPlayer(pDamager) && !plugin.getTagPlayerManager().hasPlayer(pDamaged)) {
            pDamager.sendMessage(ChatColor.RED + "This player is not in your game.");
            event.setCancelled(true);
            return;
        }
        if (!plugin.getTagPlayerManager().hasPlayer(pDamager) && plugin.getTagPlayerManager().hasPlayer(pDamaged)) {
            pDamager.sendMessage(ChatColor.RED + "You cannot harm a player who is in a game.");
            event.setCancelled(true);
            return;
        }
        TagPlayer tpDamager = plugin.getTagPlayerManager().getGPlayer(pDamager);
        TagPlayer tpDamaged = plugin.getTagPlayerManager().getGPlayer(pDamaged);
        if (tpDamager.getGameId() != tpDamaged.getGameId()) {
            tpDamager.getPlayer().sendMessage(ChatColor.RED + "You cannot tag someone who is not in your game!");
            event.setCancelled(true);
            return;
        }
        plugin.getServer().getPluginManager().callEvent(new TagPlayerHitTagPlayerEvent(event, tpDamager, tpDamaged, tpDamager.getGameId()));
    }

}
