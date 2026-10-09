package hotel.service;
import hotel.repository.RoomRepository;
import hotel.entities.Room;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelRoomManager {
    private final RoomRepository roomRepository;
    public HotelRoomManager(RoomRepository roomRepository) { this.roomRepository = roomRepository; }

    public void showRooms() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) { System.out.println("No rooms!"); return; }
        rooms.forEach(r -> System.out.println("№" + r.getNumber() + " | " + (r.isFree() ? "Free" : "Busy") + " | " + r.getType() + " | " + r.getPrice() + "$"));
    }

    public boolean addRoom(Room room) {
        if (roomRepository.existsById(room.getNumber())) return false;
        roomRepository.save(room);
        return true;
    }

    public boolean deleteRoom(int num) {
        return roomRepository.findById(num).filter(Room::isFree).map(r -> {
            roomRepository.delete(r);
            return true;
        }).orElse(false);
    }
}