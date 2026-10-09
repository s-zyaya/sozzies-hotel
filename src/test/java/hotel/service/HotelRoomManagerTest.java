package hotel.service;

import hotel.entities.Room;
import hotel.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HotelRoomManagerTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRoomManager roomManager;

    @Test
    void testAddRoom_Positive() {
        Room newRoom = new Room.Builder()
                .number(999)
                .price(100)
                .type("STD")
                .cleaningPrice(10)
                .build();
        boolean result = roomManager.addRoom(newRoom);
        assertTrue(result);
    }

    @Test
    void testDeleteRoom_Positive() {
        Room room = new Room.Builder()
                .number(101)
                .price(150)
                .type("LUX")
                .cleaningPrice(20)
                .build();
        roomRepository.save(room);

        boolean result = roomManager.deleteRoom(101);
        assertTrue(result);
    }

    @Test
    void testAddRoom_Negative_AlreadyExists() {
        roomRepository.save(new Room.Builder()
                .number(101)
                .price(150)
                .type("LUX")
                .cleaningPrice(20)
                .build());
        Room duplicateRoom = new Room.Builder()
                .number(101)
                .price(200)
                .type("SUITE")
                .cleaningPrice(30)
                .build();;
        boolean result = roomManager.addRoom(duplicateRoom);
        assertFalse(result);
    }

    @Test
    void testDeleteRoom_Negative_RoomIsBusy() {
        Room room = new Room.Builder()
                .number(202)
                .price(100)
                .type("STD")
                .cleaningPrice(10)
                .build();
        room.setFree(false);
        roomRepository.save(room);

        boolean result = roomManager.deleteRoom(202);

        assertFalse(result);
    }


}