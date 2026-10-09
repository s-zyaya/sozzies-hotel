package hotel.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaidEntityTest {

    @Test
    void testMaidCleaningProgress() {
        Maid maid = new Maid("Olga");

        maid.addCleanedRoom();
        maid.addCleanedRoom();

        assertEquals(2, maid.getCleanedRooms());
    }

    @Test
    void testWageCalculation() {
        Maid maid = new Maid("Olga");

        maid.addWage(100);
        maid.addWage(50);

        assertEquals(150, maid.getWage());

        maid.resetWage();
        assertEquals(0, maid.getWage());
    }
}