package hotel.repository;

import hotel.entities.Guest;
import hotel.entities.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReceiptRepositoryTest {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testSaveAndFindHistory() {
        Guest guest = new Guest("Kuku");
        entityManager.persist(guest);

        Receipt receipt = new Receipt(guest, 305, 1200);
        receiptRepository.save(receipt);

        List<Receipt> history = receiptRepository.findAll();

        assertFalse(history.isEmpty());
        assertEquals(1, history.size());
        assertTrue(history.get(0).toString().contains("Kuku"));
    }
}