package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptEntityTest {

    @Test
    void testReceiptToString() {
        Guest guest = new Guest("Sofia");
        Receipt receipt = new Receipt(guest, 101, 750);

        String result = receipt.toString();

        assertTrue(result.contains("Sofia"));
        assertTrue(result.contains("101"));
        assertTrue(result.contains("750$"));
    }
}