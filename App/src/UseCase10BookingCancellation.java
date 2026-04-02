import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * 
 * Demonstrates safe cancellation using Stack (LIFO) and
 * consistent rollback of inventory and booking state.
 * 
 * @author Vaishnavi
 * @version 10.0
 */

// -------------------- Booking Record --------------------
class BookingRecordUC10 {

    String reservationId;
    String customerName;
    String roomType;
    String roomId;

    BookingRecordUC10(String reservationId, String customerName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// -------------------- Inventory --------------------
class RoomInventoryUC10 {

    private HashMap<String, Integer> inventory;

    RoomInventoryUC10() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// -------------------- Cancellation Service --------------------
class CancellationService {

    private HashMap<String, BookingRecordUC10> bookingMap;
    private Stack<String> rollbackStack;
    private RoomInventoryUC10 inventory;

    CancellationService(RoomInventoryUC10 inventory) {
        this.inventory = inventory;
        this.bookingMap = new HashMap<>();
        this.rollbackStack = new Stack<>();
    }

    // Add confirmed booking (simulate UC6 output)
    public void addBooking(BookingRecordUC10 record) {
        bookingMap.put(record.reservationId, record);
    }

    // Cancel booking
    public void cancelBooking(String reservationId) {

        if (!bookingMap.containsKey(reservationId)) {
            System.out.println("❌ Cancellation failed: Reservation not found");
            return;
        }

        BookingRecordUC10 record = bookingMap.get(reservationId);

        // Push room ID to stack (LIFO rollback tracking)
        rollbackStack.push(record.roomId);

        // Restore inventory
        inventory.increaseAvailability(record.roomType);

        // Remove booking
        bookingMap.remove(reservationId);

        System.out.println("✅ Booking cancelled: " + reservationId);
        System.out.println("Room Released: " + record.roomId);
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// -------------------- Main --------------------
class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Cancellation System v10.0 ===\n");

        RoomInventoryUC10 inventory = new RoomInventoryUC10();

        CancellationService service = new CancellationService(inventory);

        // Simulate confirmed bookings
        service.addBooking(new BookingRecordUC10("RES101", "Vaishnavi", "Single", "S101"));
        service.addBooking(new BookingRecordUC10("RES102", "Rahul", "Double", "D201"));

        // Perform cancellation
        service.cancelBooking("RES101");

        // Invalid cancellation
        service.cancelBooking("RES999");

        // Show rollback stack
        service.displayRollbackStack();

        // Show updated inventory
        inventory.displayInventory();
    }
}