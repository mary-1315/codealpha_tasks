import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    boolean available;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.available = true;
    }
}

class Booking {
    String customerName;
    int roomNumber;
    String category;

    Booking(String customerName, int roomNumber, String category) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    saveBookings();
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);
    }

    static void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room " + r.roomNumber + " - " + r.category);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter Customer Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        viewRooms();

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.available) {

                System.out.print("Enter Payment Amount: ");
                double payment = sc.nextDouble();

                System.out.println("Payment Successful: Rs." + payment);

                r.available = false;
                bookings.add(new Booking(name, roomNo, r.category));

                System.out.println("Room Booked Successfully!");
                return;
            }
        }

        System.out.println("Room Not Available!");
    }

    static void cancelReservation() {
        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {
            Booking b = iterator.next();

            if (b.roomNumber == roomNo) {
                iterator.remove();

                for (Room r : rooms) {
                    if (r.roomNumber == roomNo) {
                        r.available = true;
                    }
                }

                System.out.println("Reservation Cancelled!");
                return;
            }
        }

        System.out.println("Booking Not Found!");
    }

    static void viewBookings() {
        System.out.println("\nBooking Details:");

        for (Booking b : bookings) {
            System.out.println("Customer: " + b.customerName);
            System.out.println("Room No: " + b.roomNumber);
            System.out.println("Category: " + b.category);
            System.out.println("-------------------");
        }
    }

    static void saveBookings() {
        try {
            FileWriter fw = new FileWriter("bookings.txt");

            for (Booking b : bookings) {
                fw.write(b.customerName + ","
                        + b.roomNumber + ","
                        + b.category + "\n");
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Error Saving File");
        }
    }
}