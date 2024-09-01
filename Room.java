import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Room {
    private String roomNumber;
    private String category; // e.g., Single, Double, Suite
    private boolean isAvailable;

    public Room(String roomNumber, String category, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - " + (isAvailable ? "Available" : "Not Available");
    }
}

 class Booking {
    private Room room;
    private String customerName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalAmount;

    public Booking(Room room, String customerName, LocalDate checkInDate, LocalDate checkOutDate, double totalAmount) {
        this.room = room;
        this.customerName = customerName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
    }

    public Room getRoom() {
        return room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Booking Details:\n" +
                "Room: " + room.getRoomNumber() + "\n" +
                "Customer: " + customerName + "\n" +
                "Check-in: " + checkInDate + "\n" +
                "Check-out: " + checkOutDate + "\n" +
                "Total Amount: $" + totalAmount;
    }
}




class Hotel {
    private Map<String, Room> rooms; // Room number as key
    private List<Booking> bookings;

    public Hotel() {
        rooms = new HashMap<>();
        bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public Room getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public List<Room> searchAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public boolean makeReservation(Room room, String customerName, LocalDate checkInDate, LocalDate checkOutDate, double amount) {
        if (room.isAvailable()) {
            room.setAvailable(false);
            Booking booking = new Booking(room, customerName, checkInDate, checkOutDate, amount);
            bookings.add(booking);
            return true;
        } else {
            return false;
        }
    }

    public Booking viewBooking(String roomNumber) {
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber().equals(roomNumber)) {
                return booking;
            }
        }
        return null;
    }
}

 class Payment {
    public static boolean processPayment(double amount) {
        // Simulate payment processing
        // In a real application, integrate with a payment gateway API
        return amount > 0;
    }
}



class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        // Add some rooms to the hotel
        hotel.addRoom(new Room("101", "Single", true));
        hotel.addRoom(new Room("102", "Double", true));
        hotel.addRoom(new Room("201", "Suite", true));
        hotel.addRoom(new Room("202", "Suite", false));

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room category (Single, Double, Suite): ");
                    String category = scanner.nextLine();
                    List<Room> availableRooms = hotel.searchAvailableRooms(category);
                    System.out.println("Available Rooms:");
                    for (Room room : availableRooms) {
                        System.out.println(room);
                    }
                    break;

                case 2:
                    System.out.print("Enter room number to book: ");
                    String roomNumber = scanner.nextLine();
                    Room room = hotel.getRoom(roomNumber);
                    if (room != null && room.isAvailable()) {
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter check-in date (YYYY-MM-DD): ");
                        LocalDate checkInDate = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter check-out date (YYYY-MM-DD): ");
                        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine());
                        double amount = 100.00; // Fixed amount for simplicity

                        if (Payment.processPayment(amount)) {
                            if (hotel.makeReservation(room, name, checkInDate, checkOutDate, amount)) {
                                System.out.println("Reservation successful!");
                            } else {
                                System.out.println("Failed to make reservation. Room may not be available.");
                            }
                        } else {
                            System.out.println("Payment processing failed.");
                        }
                    } else {
                        System.out.println("Room not found or not available.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Room number to view booking: ");
                    roomNumber = scanner.nextLine();
                    Booking booking = hotel.viewBooking(roomNumber);
                    if (booking != null) {
                        System.out.println(booking);
                    } else {
                        System.out.println("No booking found for this room number.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting...!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
