import java.util.HashMap;

/**
 * Use Case 4: Room Search & Availability Check
 * 
 * Demonstrates read-only access to inventory and safe retrieval
 * of available room details without modifying system state.
 * 
 * @author Vaishnavi
 * @version 4.0
 */

// -------------------- Room Domain --------------------
abstract class Room {

    int beds;
    int size;
    double price;

    Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    void displayDetails(String type) {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price: ₹" + price);
        System.out.println();
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super(1, 250, 1500);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super(2, 400, 2500);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super(3, 750, 5000);
    }
}

// -------------------- Inventory --------------------
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 0); // unavailable
        inventory.put("Suite", 2);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public HashMap<String, Integer> getAllInventory() {
        return inventory; // read-only usage
    }
}

// -------------------- Search Service --------------------
class SearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        System.out.println("=== Available Rooms ===\n");

        HashMap<String, Integer> data = inventory.getAllInventory();

        for (String type : data.keySet()) {

            int count = data.get(type);

            // Filter unavailable rooms
            if (count > 0) {

                Room room = null;

                // Polymorphism
                if (type.equals("Single")) {
                    room = new SingleRoom();
                } else if (type.equals("Double")) {
                    room = new DoubleRoom();
                } else if (type.equals("Suite")) {
                    room = new SuiteRoom();
                }

                if (room != null) {
                    room.displayDetails(type);
                    System.out.println("Available: " + count);
                    System.out.println("----------------------");
                }
            }
        }
    }
}

// -------------------- Main --------------------
class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Search System v4.0 ===\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search service (read-only)
        SearchService searchService = new SearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory);
    }
}
