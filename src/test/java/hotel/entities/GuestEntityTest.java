package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GuestEntityTest {

    @Test
    void testAddServiceToGuest() {
        Guest guest = new Guest("Marta");
        HotelService laundry = new HotelService("Laundry", 20);

        guest.addService(laundry);

        assertEquals(1, guest.getServices().size());
        assertTrue(guest.getServices().contains(laundry));
    }

    @Test
    void testGuestIdentity() {
        Guest guest1 = new Guest("Sofia");
        Guest guest2 = new Guest("Sofia");

        assertEquals(guest1, guest2);
    }
}