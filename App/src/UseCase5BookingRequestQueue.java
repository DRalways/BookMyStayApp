import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request Queue (FIFO)
 * 
 * Demonstrates fair handling of booking requests using Queue.
 * Requests are stored in arrival order and processed later.
 * 
 * @author Vaishnavi
 * @version 5.0
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

    void display() {
        System.out.println("Request ID: " + requestId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType);
    }
}

// -------------------- Booking Queue --------------------
class BookingQueue {

    private Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("✅ Request added to queue: " + r.customerName);
    }

    // View all requests (without removing)
    public void viewQueue() {
        System.out.println("\n=== Booking Request Queue ===");
        for (Reservation r : queue) {
            r.display();
        }
        System.out.println();
    }
}

// -------------------- Main --------------------
class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Booking Queue v5.0 ===\n");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulating incoming requests
        bookingQueue.addRequest(new Reservation(1, "Vaishnavi", "Single"));
        bookingQueue.addRequest(new Reservation(2, "Rahul", "Double"));
        bookingQueue.addRequest(new Reservation(3, "Ananya", "Suite"));

        // Display queue (FIFO order)
        bookingQueue.viewQueue();
    }
}
