package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomEntityTest {

    @Test
    void testRoomBuilder() {
        Room room = new Room.Builder()
                .number(101)
                .price(200)
                .type("LUX")
                .cleaningPrice(50)
                .build();

        assertEquals(101, room.getNumber());
        assertEquals(200, room.getPrice());
        assertEquals("LUX", room.getType());
        assertTrue(room.isFree()); // Значення за замовчуванням
    }
}