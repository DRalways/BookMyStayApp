import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * 
 * Demonstrates saving and restoring system state using serialization.
 * Ensures data survives application restart.
 * 
 * @author Vaishnavi
 * @version 12.0
 */

// -------------------- Booking Record --------------------
class BookingRecordUC12 implements Serializable {

    private static final long serialVersionUID = 1L;

    String reservationId;
    String customerName;
    String roomType;

    BookingRecordUC12(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// -------------------- Inventory --------------------
class RoomInventoryUC12 implements Serializable {

    private static final long serialVersionUID = 1L;

    HashMap<String, Integer> inventory;

    RoomInventoryUC12() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public void displayInventory() {
        System.out.println("\n=== Inventory ===");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// -------------------- System State --------------------
class SystemStateUC12 implements Serializable {

    private static final long serialVersionUID = 1L;

    List<BookingRecordUC12> bookings;
    RoomInventoryUC12 inventory;

    SystemStateUC12(List<BookingRecordUC12> bookings, RoomInventoryUC12 inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// -------------------- Persistence Service --------------------
class PersistenceServiceUC12 {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemStateUC12 state) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("✅ System state saved successfully!");

        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public SystemStateUC12 load() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("✅ System state loaded successfully!");
            return (SystemStateUC12) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("⚠ No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading state: " + e.getMessage());
        }

        return null;
    }
}

// -------------------- Main --------------------
class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Persistence System v12.0 ===\n");

        PersistenceServiceUC12 persistence = new PersistenceServiceUC12();

        // Try loading previous state
        SystemStateUC12 state = persistence.load();

        if (state == null) {

            // First run → create fresh data
            List<BookingRecordUC12> bookings = new ArrayList<>();
            RoomInventoryUC12 inventory = new RoomInventoryUC12();

            bookings.add(new BookingRecordUC12("RES101", "Vaishnavi", "Single"));
            bookings.add(new BookingRecordUC12("RES102", "Rahul", "Double"));

            state = new SystemStateUC12(bookings, inventory);

            System.out.println("Initialized new system state.");
        }

        // Display data
        System.out.println("\n=== Booking Records ===");
        for (BookingRecordUC12 b : state.bookings) {
            System.out.println(b.reservationId + " | " + b.customerName + " | " + b.roomType);
        }

        state.inventory.displayInventory();

        // Save state before exit
        persistence.save(state);
    }
}