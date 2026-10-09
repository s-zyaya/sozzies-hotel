package hotel.service;

import hotel.entities.Guest;
import hotel.entities.Room;
import hotel.repository.RoomRepository;
import hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReceptionServiceTest {

    @Autowired
    private ReceptionService receptionService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testCalculateFinalPrice() {
        Room room = new Room.Builder()
                .number(101)
                .price(200)
                .type("Standart")
                .cleaningPrice(50)
                .build();
        room.setCleaningTimes(2);
        Guest guest = new Guest("NifNif");
        guest.setNights(3);

        int totalPrice = receptionService.calculateFinalPrice(room, guest);
        assertEquals(700, totalPrice);
    }

    @Test
    void testFinalizeCheckOut() {
        Room room = new Room.Builder()
                .number(103)
                .price(300)
                .type("Comfort")
                .cleaningPrice(30)
                .build();
        room.setFree(false);
        room = roomRepository.save(room);

        Guest guest = new Guest("NafNaf");
        guest = userRepository.save(guest);

        receptionService.finalizeCheckOut(guest, room);

        assertTrue(roomRepository.findById(103).get().isFree());
        assertFalse(userRepository.findByName("NafNaf").isPresent());
    }

    @Test
    void testCheckIn_ShouldOccupiedRoom() {
        Room room = new Room.Builder()
                .number(505)
                .price(100)
                .type("STD")
                .cleaningPrice(10)
                .build();
        roomRepository.save(room);
        Guest guest = new Guest("Ivan");

        receptionService.checkIn(guest, 505, 2);

        Room updatedRoom = roomRepository.findById(505).get();
        assertFalse(updatedRoom.isFree());
        assertEquals(505, guest.getRoomNum());
    }
}