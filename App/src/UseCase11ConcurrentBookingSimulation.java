import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 * 
 * Demonstrates thread safety using synchronization to prevent
 * race conditions during concurrent booking.
 * 
 * @author Vaishnavi
 * @version 11.0
 */

// -------------------- Reservation --------------------
class ReservationUC11 {

    String customerName;
    String roomType;

    ReservationUC11(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

// -------------------- Inventory --------------------
class RoomInventoryUC11 {

    private HashMap<String, Integer> inventory;

    RoomInventoryUC11() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Critical Section → only one thread at a time
    public synchronized boolean allocateRoom(String type) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }

        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("\n=== Final Inventory ===");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

// -------------------- Booking Processor (Thread) --------------------
class BookingProcessorUC11 extends Thread {

    private Queue<ReservationUC11> queue;
    private RoomInventoryUC11 inventory;

    BookingProcessorUC11(Queue<ReservationUC11> queue, RoomInventoryUC11 inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            ReservationUC11 reservation;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                reservation = queue.poll();
            }

            // Try booking
            boolean success = inventory.allocateRoom(reservation.roomType);

            if (success) {
                System.out.println("✅ Booking Confirmed: " + reservation.customerName +
                        " (" + reservation.roomType + ")");
            } else {
                System.out.println("❌ Booking Failed: " + reservation.customerName +
                        " (" + reservation.roomType + ")");
            }

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// -------------------- Main --------------------
class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Concurrent Booking System v11.0 ===\n");

        // Shared resources
        Queue<ReservationUC11> queue = new LinkedList<>();
        RoomInventoryUC11 inventory = new RoomInventoryUC11();

        // Add booking requests
        queue.add(new ReservationUC11("Vaishnavi", "Single"));
        queue.add(new ReservationUC11("Rahul", "Single"));
        queue.add(new ReservationUC11("Ananya", "Single")); // should fail
        queue.add(new ReservationUC11("Kiran", "Double"));
        queue.add(new ReservationUC11("Amit", "Double")); // should fail

        // Create multiple threads
        BookingProcessorUC11 t1 = new BookingProcessorUC11(queue, inventory);
        BookingProcessorUC11 t2 = new BookingProcessorUC11(queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final inventory
        inventory.displayInventory();
    }
}