import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 * Clean, conflict-free implementation
 */

// -------------------- Reservation Record --------------------
class BookingRecordUC8 {

    private String reservationId;
    private String customerName;
    private String roomType;

    BookingRecordUC8(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Customer: " + customerName +
                " | Room: " + roomType);
    }
}

// -------------------- Booking History --------------------
class BookingHistoryUC8 {

    private List<BookingRecordUC8> records;

    BookingHistoryUC8() {
        records = new ArrayList<>();
    }

    // Add booking record
    public void addBooking(BookingRecordUC8 record) {
        records.add(record);
    }

    // Return all records
    public List<BookingRecordUC8> getAllBookings() {
        return records;
    }
}

// -------------------- Report Service --------------------
class BookingReportServiceUC8 {

    public void generateReport(List<BookingRecordUC8> records) {

        System.out.println("\n=== Booking History Report ===");

        if (records.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (BookingRecordUC8 record : records) {
            record.display();
        }

        System.out.println("\nTotal Bookings: " + records.size());
    }
}

// -------------------- Main --------------------
class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=== BookMyStay Reporting System v8.0 ===\n");

        BookingHistoryUC8 history = new BookingHistoryUC8();

        // Simulated confirmed bookings
        history.addBooking(new BookingRecordUC8("RES101", "Vaishnavi", "Single"));
        history.addBooking(new BookingRecordUC8("RES102", "Rahul", "Double"));
        history.addBooking(new BookingRecordUC8("RES103", "Ananya", "Suite"));

        // Generate report
        BookingReportServiceUC8 reportService = new BookingReportServiceUC8();
        reportService.generateReport(history.getAllBookings());
    }
}
