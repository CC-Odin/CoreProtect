package net.coreprotect.listener.entity;

import java.util.Locale;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.InventoryHolder;

import net.coreprotect.config.Config;
import net.coreprotect.consumer.Queue;
import net.coreprotect.database.Database;
import net.coreprotect.utility.EntityUtils;

public final class VehicleDestroyListener extends Queue implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Entity vehicle = event.getVehicle();

        Material vehicleMaterial = EntityUtils.getEntityMaterial(vehicle.getType());
        if (vehicleMaterial == null) {
            return;
        }

        Config config = Config.getConfig(vehicle.getWorld());
        boolean logBreak = config.BLOCK_BREAK;
        boolean logItems = config.ITEM_TRANSACTIONS;

        if (!logBreak && !logItems) {
            return;
        }

        // Determine who destroyed the vehicle
        String user = "";
        Entity attacker = event.getAttacker();
        if (attacker != null) {
            user = EntityUtils.resolveAttacker(attacker);
        }

        if (user.isEmpty()) {
            user = "#" + vehicle.getType().name().toLowerCase(Locale.ROOT);
        }

        Location location = vehicle.getLocation();

        // Log the vehicle break
        if (logBreak) {
            Queue.queueBlockBreak(user, location.getBlock().getState(), vehicleMaterial, null, 0);
        }

        // Log container contents for container vehicles
        if (logItems && vehicle instanceof InventoryHolder) {
            Database.containerBreakCheck(user, vehicleMaterial, vehicle, null, location);
        }
    }
}
