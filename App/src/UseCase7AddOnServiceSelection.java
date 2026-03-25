import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * 
 * Demonstrates attaching optional services to reservations
 * without modifying core booking or inventory logic.
 * 
 * Uses Map + List to model one-to-many relationship.
 * 
 * @author Vaishnavi
 * @version 7.0
 */

// -------------------- Service --------------------
class Service {

    String serviceName;
    double price;

    Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    void display() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

// -------------------- Add-On Service Manager --------------------
class AddOnServiceManager {

    // Map: ReservationID → List of Services
    private HashMap<String, List<Service>> serviceMap;

    AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());

        serviceMap.get(reservationId).add(service);

        System.out.println("✅ Service added to Reservation " + reservationId + ": " + service.serviceName);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\n=== Services for Reservation " + reservationId + " ===");

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            s.display();
        }
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {

        List<Service> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (Service s : services) {
            total += s.price;
        }

        return total;
    }
}

// -------------------- Main --------------------
class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Add-On Service System v7.0 ===\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Simulated reservation IDs (from UC6)
        String res1 = "RES101";
        String res2 = "RES102";

        // Add services
        manager.addService(res1, new Service("Breakfast", 500));
        manager.addService(res1, new Service("Airport Pickup", 1200));
        manager.addService(res2, new Service("Spa Access", 2000));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" + manager.calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for " + res2 + ": ₹" + manager.calculateTotalCost(res2));
    }
}