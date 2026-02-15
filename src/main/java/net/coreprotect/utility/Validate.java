package net.coreprotect.utility;

import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Boat;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;

public class Validate {

    private Validate() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isHopper(InventoryHolder inventoryHolder) {
        return (inventoryHolder instanceof Hopper || inventoryHolder instanceof HopperMinecart);
    }

    public static boolean isDropper(InventoryHolder inventoryHolder) {
        return (inventoryHolder instanceof Dropper);
    }

    /* check if valid hopper destination */
    public static boolean isContainer(InventoryHolder inventoryHolder) {
        return (inventoryHolder instanceof BlockInventoryHolder || inventoryHolder instanceof DoubleChest);
    }

    /* check if inventory holder is an entity container */
    public static boolean isEntityContainer(InventoryHolder inventoryHolder) {
        if (inventoryHolder instanceof StorageMinecart || inventoryHolder instanceof HopperMinecart) {
            return true; // Chest minecart and hopper minecart
        }
        if (inventoryHolder instanceof Boat) {
            try {
                // Check if it's a chest boat (1.19+)
                // Chest boats implement InventoryHolder in newer versions
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        if (inventoryHolder instanceof ChestedHorse) {
            try {
                // Donkeys, mules, llamas - only log if they're carrying a chest
                ChestedHorse chestedHorse = (ChestedHorse) inventoryHolder;
                return chestedHorse.isCarryingChest();
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
