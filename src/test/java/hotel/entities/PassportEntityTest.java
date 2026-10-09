package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassportEntityTest {

    @Test
    void testPassportGuestLink() {
        Passport passport = new Passport("AB123456");
        Guest guest = new Guest("Sofia");

        passport.setGuest(guest);
        guest.setPassport(passport);

        assertEquals("AB123456", guest.getPassport().getSerialNumber());
        assertEquals(guest, passport.getGuest());
    }
}