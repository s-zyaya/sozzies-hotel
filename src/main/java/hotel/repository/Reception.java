package hotel.repository;

import hotel.entities.Guest;
import hotel.entities.Room;

public interface Reception {
    boolean isRoomAvailable(int roomNum);
    void checkIn(Guest guest, int roomNum, int nights);
    Room getGuestRoom(Guest guest, int roomNum);
    int calculateFinalPrice(Room room, Guest guest);
    void finalizeCheckOut(Guest guest, Room room);
}