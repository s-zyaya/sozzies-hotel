package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminEntityTest {

    @Test
    void testAdminInitialization() {
        Admin admin = new Admin("Viktor");

        assertNotNull(admin);
        assertEquals("Viktor", admin.getName());
    }
}