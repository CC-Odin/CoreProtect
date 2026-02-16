package net.coreprotect.listener.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPlaceEvent;

import net.coreprotect.config.Config;
import net.coreprotect.consumer.Queue;

public final class EntityPlaceListener extends Queue implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityPlace(EntityPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!Config.getConfig(event.getEntity().getWorld()).BLOCK_PLACE) {
            return;
        }

        Player player = event.getPlayer();
        Entity entity = event.getEntity();

        // Only handle boats and minecarts (armor stands and end crystals are handled elsewhere)
        if (player == null || (!(entity instanceof Boat) && !(entity instanceof Minecart))) {
            return;
        }

        Material item = player.getInventory().getItem(event.getHand()).getType();
        Location location = entity.getLocation();

        Queue.queueBlockPlace(player.getName(), location.getBlock().getState(), location.getBlock().getType(), location.getBlock().getState(), item, 0, 1, null);
    }

}
