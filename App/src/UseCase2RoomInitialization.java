/**
 * Use Case 2: Basic Room Types & Static Availability
 * 
 * Demonstrates abstraction, inheritance, and polymorphism
 * in modeling hotel room types.
 * 
 * @author Vaishnavi
 * @version 2.0
 */

// Abstract class
abstract class Room {

    int beds;
    int size;
    double price;

    // Constructor
    Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Method to display room details
    void displayDetails(String roomType, int availability) {
        System.out.println(roomType + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + availability);
        System.out.println();
    }
}

// Single Room
class SingleRoom extends Room {
    SingleRoom() {
        super(1, 250, 1500.0);
    }
}

// Double Room
class DoubleRoom extends Room {
    DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

// Suite Room
class SuiteRoom extends Room {
    SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// Main Class
class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Room System v2.0 ===\n");

        // Creating objects (Polymorphism)
        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        single.displayDetails("Single", singleAvailable);
        doub.displayDetails("Double", doubleAvailable);
        suite.displayDetails("Suite", suiteAvailable);
    }
}