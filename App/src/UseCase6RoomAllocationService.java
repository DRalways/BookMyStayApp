import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * 
 * Demonstrates allocation of rooms from booking queue while ensuring:
 * - FIFO processing
 * - Unique room assignment
 * - Inventory consistency
 * 
 * @author Vaishnavi
 * @version 6.0
 */

// -------------------- Reservation --------------------
class Reservation {

    int requestId;
    String customerName;
    String roomType;

    Reservation(int requestId, String customerName, String roomType) {
        this.requestId = requestId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// -------------------- Inventory --------------------
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Updated Inventory ===");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// -------------------- Booking Service --------------------
class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    // Track allocated room IDs per type
    private HashMap<String, Set<String>> allocatedRooms;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.queue = new LinkedList<>();
        this.allocatedRooms = new HashMap<>();
    }

    // Add booking request
    public void addRequest(Reservation r) {
        queue.add(r);
    }

    // Process bookings (FIFO)
    public void processBookings() {

        System.out.println("=== Processing Booking Requests ===\n");

        while (!queue.isEmpty()) {

            Reservation r = queue.poll(); // FIFO

            String type = r.roomType;

            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = type.substring(0, 1).toUpperCase() + UUID.randomUUID().toString().substring(0, 4);

                // Initialize set if not exists
                allocatedRooms.putIfAbsent(type, new HashSet<>());

                // Ensure uniqueness
                if (!allocatedRooms.get(type).contains(roomId)) {

                    allocatedRooms.get(type).add(roomId);

                    // Update inventory immediately
                    inventory.reduceAvailability(type);

                    System.out.println("✅ Booking Confirmed!");
                    System.out.println("Customer: " + r.customerName);
                    System.out.println("Room Type: " + type);
                    System.out.println("Assigned Room ID: " + roomId);
                    System.out.println("--------------------------");

                }

            } else {
                System.out.println("❌ Booking Failed (No Availability): " + r.customerName + " (" + type + ")");
            }
        }
    }
}

// -------------------- Main --------------------
class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Allocation System v6.0 ===\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Add requests (FIFO)
        bookingService.addRequest(new Reservation(1, "Vaishnavi", "Single"));
        bookingService.addRequest(new Reservation(2, "Rahul", "Single"));
        bookingService.addRequest(new Reservation(3, "Ananya", "Single")); // should fail
        bookingService.addRequest(new Reservation(4, "Kiran", "Suite"));

        // Process bookings
        bookingService.processBookings();

        // Show updated inventory
        inventory.displayInventory();
    }
}
