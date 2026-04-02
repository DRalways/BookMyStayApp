import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 * 
 * Demonstrates validation and fail-fast design using custom exceptions
 * to prevent invalid bookings and protect system state.
 * 
 * @author Vaishnavi
 * @version 9.0
 */

// -------------------- Custom Exception --------------------
class InvalidBookingException extends Exception {

    InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------- Inventory --------------------
class RoomInventoryUC9 {

    private HashMap<String, Integer> inventory;

    RoomInventoryUC9() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 0); // intentionally unavailable
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceAvailability(String type) throws InvalidBookingException {

        int current = getAvailability(type);

        if (current <= 0) {
            throw new InvalidBookingException("No availability for room type: " + type);
        }

        inventory.put(type, current - 1);
    }
}

// -------------------- Validator --------------------
class BookingValidator {

    private static final List<String> VALID_TYPES = Arrays.asList("Single", "Double", "Suite");

    public static void validateRoomType(String type) throws InvalidBookingException {

        if (!VALID_TYPES.contains(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
    }
}

// -------------------- Booking Service --------------------
class BookingServiceUC9 {

    private RoomInventoryUC9 inventory;

    BookingServiceUC9(RoomInventoryUC9 inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String customerName, String roomType) {

        try {

            // Step 1: Validate input
            BookingValidator.validateRoomType(roomType);

            // Step 2: Check availability
            if (inventory.getAvailability(roomType) == -1) {
                throw new InvalidBookingException("Room type does not exist.");
            }

            // Step 3: Allocate room (fail-fast)
            inventory.reduceAvailability(roomType);

            // Step 4: Success
            System.out.println("✅ Booking confirmed for " + customerName + " (" + roomType + ")");

        } catch (InvalidBookingException e) {

            // Graceful failure
            System.out.println("❌ Booking failed for " + customerName + ": " + e.getMessage());
        }
    }
}

// -------------------- Main --------------------
class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Validation System v9.0 ===\n");

        RoomInventoryUC9 inventory = new RoomInventoryUC9();
        BookingServiceUC9 bookingService = new BookingServiceUC9(inventory);

        // Valid booking
        bookingService.bookRoom("Vaishnavi", "Single");

        // Invalid room type
        bookingService.bookRoom("Rahul", "Deluxe");

        // No availability
        bookingService.bookRoom("Ananya", "Suite");

        // Another valid booking
        bookingService.bookRoom("Kiran", "Double");
    }
}