package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HotelServiceEntityTest {

    @Test
    void testServiceCreation() {
        HotelService service = new HotelService("Breakfast", 15);

        assertEquals("Breakfast", service.getName());
        assertEquals(15, service.getPrice());
    }
}