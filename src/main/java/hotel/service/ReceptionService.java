package hotel.service;
import hotel.repository.RoomRepository;
import hotel.repository.UserRepository;
import hotel.entities.Guest;
import hotel.entities.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceptionService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReceptionService(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public boolean isRoomAvailable(int num) {
        return roomRepository.findById(num).map(Room::isFree).orElse(false);
    }
    @Transactional
    public void checkIn(Guest guest, int num, int nights) {
        roomRepository.findById(num).ifPresent(r -> {
            r.setFree(false);
            guest.setRoomNum(num);
            guest.setNights(nights);
            roomRepository.save(r);
            userRepository.save(guest);
        });
    }
    public Room getGuestRoom(Guest guest, int num) {
        return roomRepository.findById(num).filter(r -> !r.isFree() && guest.getRoomNum() == num).orElse(null);
    }
    public int calculateFinalPrice(Room r, Guest g) {
        return r.getPrice() * g.getNights() + r.getCleaningPrice() * r.getCleaningTimes();
    }
    @Transactional
    public void finalizeCheckOut(Guest guest, Room room) {
        room.setFree(true);
        room.setCleaningTimes(0);
        roomRepository.save(room);
        userRepository.delete(guest);
    }
}