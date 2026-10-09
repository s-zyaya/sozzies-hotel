package hotel.service;
import hotel.repository.RoomRepository;
import hotel.repository.UserRepository;
import hotel.entities.Maid;
import hotel.entities.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CleaningService {
    private final RoomRepository roomRepository;
    public CleaningService(RoomRepository roomRepository) { this.roomRepository = roomRepository; }

    public Room checkRoomForCleaningCall(int rnum) {
        return roomRepository.findById(rnum).filter(r -> !r.isFree()).orElse(null);
    }

    public void confirmCleaningCall(Room room) {
        room.setNeedsCleaning(true);
        room.setCleaningTimes(room.getCleaningTimes() + 1);
        roomRepository.save(room);
    }

    public List<Room> getRoomsNeedsCleaning() {
        return roomRepository.findAll().stream().filter(Room::isNeedsCleaning).collect(Collectors.toList());
    }

    @Transactional
    public int cleanTheRoom(Maid maid, int roomNum, UserRepository userRepository) {
        return roomRepository.findById(roomNum).map(room -> {
            if (room.isNeedsCleaning()) {
                room.setNeedsCleaning(false);
                roomRepository.save(room);
                maid.addCleanedRoom();
                maid.addWage(room.getCleaningPrice());
                userRepository.save(maid);
                return 1;
            }
            return 0;
        }).orElse(-1);
    }
}