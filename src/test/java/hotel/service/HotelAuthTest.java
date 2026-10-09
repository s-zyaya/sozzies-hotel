package hotel.service;

import hotel.entities.Maid;
import hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelAuthTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HotelAuth hotelAuth;

    @Test
    void testAdminPasswordCheck() {
        assertTrue(hotelAuth.adminPasswordCheck("ziaia222"));
        assertFalse(hotelAuth.adminPasswordCheck("wrong_pass"));
    }

    @Test
    void testMaidCheck_Positive() {
        String name = "Olena";
        when(userRepository.findByName(name)).thenReturn(Optional.of(new Maid(name)));

        assertNotNull(hotelAuth.maidCheck(name));
    }
}