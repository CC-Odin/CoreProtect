package net.coreprotect.utility;

import org.bukkit.Material;
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
            // If a Boat implements InventoryHolder, it must be a chest boat (1.19+)
            // Regular boats don't implement InventoryHolder
            return true;
        }
        if (inventoryHolder instanceof ChestedHorse) {
            // Donkeys, mules, llamas - only log if they're carrying a chest
            ChestedHorse chestedHorse = (ChestedHorse) inventoryHolder;
            return chestedHorse.isCarryingChest();
        }
        return false;
    }
    
    /* check if material represents an entity container (for logging purposes) */
    public static boolean isEntityContainerMaterial(Material type) {
        if (type == null) {
            return false;
        }
        switch (type.name()) {
            case "CHEST_MINECART":
            case "HOPPER_MINECART":
            case "OAK_CHEST_BOAT":
            case "SPRUCE_CHEST_BOAT":
            case "BIRCH_CHEST_BOAT":
            case "JUNGLE_CHEST_BOAT":
            case "ACACIA_CHEST_BOAT":
            case "DARK_OAK_CHEST_BOAT":
            case "MANGROVE_CHEST_BOAT":
            case "CHERRY_CHEST_BOAT":
            case "BAMBOO_CHEST_RAFT":
            case "LLAMA_SPAWN_EGG":
            case "DONKEY_SPAWN_EGG":
            case "MULE_SPAWN_EGG":
                return true;
            default:
                return false;
        }
    }

}
