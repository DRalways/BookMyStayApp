import java.util.HashMap;

/**
 * Use Case 3: Centralized Room Inventory Management
 * 
 * Demonstrates how HashMap is used to manage room availability
 * as a single source of truth.
 * 
 * @author Vaishnavi
 * @version 3.0
 */

// Inventory class (Encapsulation of logic)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor → initialize inventory
    RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("=== Current Room Inventory ===");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
        System.out.println();
    }
}

// Main class
class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Inventory System v3.0 ===\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example update
        System.out.println("Booking 1 Single Room...\n");
        int current = inventory.getAvailability("Single");
        inventory.updateAvailability("Single", current - 1);

        // Display updated inventory
        inventory.displayInventory();
    }
}