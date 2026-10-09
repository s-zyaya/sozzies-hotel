package hotel.repository;

import hotel.entities.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testSaveAndFindRoom() {
        Room room = new Room.Builder()
                .number(202)
                .price(300)
                .type("LUX")
                .build();

        roomRepository.save(room);

        Room found = roomRepository.findById(202).orElse(null);
        assertNotNull(found);
        assertEquals(300, found.getPrice());
        assertEquals("LUX", found.getType());
    }

    @Test
    void testFindAllRooms() {
        Room r1 = new Room.Builder().number(1).build();
        Room r2 = new Room.Builder().number(2).build();
        roomRepository.save(r1);
        roomRepository.save(r2);

        List<Room> rooms = roomRepository.findAll();
        assertTrue(rooms.size() >= 2);
    }
}