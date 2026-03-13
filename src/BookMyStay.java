import java.util.*;

class Room {
    private int roomId;
    private String type;
    private boolean isAvailable;

    public Room(int roomId, String type) {
        this.roomId = roomId;
        this.type = type;
        this.isAvailable = true;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        isAvailable = false;
    }

    public void releaseRoom() {
        isAvailable = true;
    }

    public String toString() {
        return "Room ID: " + roomId + " | Type: " + type + " | Available: " + isAvailable;
    }
}

class BookingRequest {
    private String customerName;
    private String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class Booking {
    private String customerName;
    private int roomId;

    public Booking(String customerName, int roomId) {
        this.customerName = customerName;
        this.roomId = roomId;
    }

    public String toString() {
        return "Booking -> Customer: " + customerName + ", Room ID: " + roomId;
    }
}

class HotelBookingSystem {

    private Map<Integer, Room> rooms = new HashMap<>();
    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Set<Integer> bookedRooms = new HashSet<>();
    private List<Booking> confirmedBookings = new ArrayList<>();

    public void addRoom(int id, String type) {
        rooms.put(id, new Room(id, type));
    }

    public void requestBooking(String customer, String roomType) {
        bookingQueue.add(new BookingRequest(customer, roomType));
        System.out.println(customer + " added to booking queue.");
    }

    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            BookingRequest request = bookingQueue.poll();
            boolean booked = false;

            for (Room room : rooms.values()) {

                if (room.isAvailable() && room.getType().equalsIgnoreCase(request.getRoomType())) {

                    if (!bookedRooms.contains(room.getRoomId())) {

                        room.bookRoom();
                        bookedRooms.add(room.getRoomId());

                        Booking booking = new Booking(request.getCustomerName(), room.getRoomId());
                        confirmedBookings.add(booking);

                        System.out.println("Booking Confirmed: " + booking);

                        booked = true;
                        break;
                    }
                }
            }

            if (!booked) {
                System.out.println("No room available for " + request.getCustomerName());
            }
        }
    }

    public void cancelBooking(int roomId) {

        if (rooms.containsKey(roomId)) {

            rooms.get(roomId).releaseRoom();
            bookedRooms.remove(roomId);

            System.out.println("Booking cancelled for Room " + roomId);
        }
    }

    public void showRooms() {

        System.out.println("\n--- Room Status ---");

        for (Room room : rooms.values()) {
            System.out.println(room);
        }
    }

    public void showBookings() {

        System.out.println("\n--- Confirmed Bookings ---");

        for (Booking b : confirmedBookings) {
            System.out.println(b);
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        HotelBookingSystem system = new HotelBookingSystem();

        system.addRoom(101, "Single");
        system.addRoom(102, "Single");
        system.addRoom(201, "Double");
        system.addRoom(202, "Double");

        system.showRooms();

        system.requestBooking("Alice", "Single");
        system.requestBooking("Bob", "Double");
        system.requestBooking("Charlie", "Single");
        system.requestBooking("David", "Double");

        system.processBookings();

        system.showBookings();

        system.showRooms();

        system.cancelBooking(101);

        system.showRooms();
    }
}