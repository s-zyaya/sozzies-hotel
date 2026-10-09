package hotel.service;

import hotel.entities.Maid;
import hotel.entities.Room;
import hotel.repository.RoomRepository;
import hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CleaningServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CleaningService cleaningService;

    @Test
    void shouldCleanRoomAndRewardMaid() {
        Room room = new Room.Builder()
                .number(202)
                .price(150)
                .type("Comfort")
                .cleaningPrice(40)
                .build();
        room.setNeedsCleaning(true);
        Maid maid = new Maid("NifNif");
        when(roomRepository.findById(202)).thenReturn(Optional.of(room));

        int result = cleaningService.cleanTheRoom(maid, 202, userRepository);

        assertThat(result).isEqualTo(1);
        assertThat(room.isNeedsCleaning()).isFalse();
        assertThat(maid.getCleanedRooms()).isEqualTo(1);
        assertThat(maid.getWage()).isEqualTo(40);
        verify(roomRepository).save(room);
        verify(userRepository).save(maid);
    }

    @Test
    void shouldReturnZeroIfRoomAlreadyClean() {
        Room room = new Room.Builder()
                .number(205)
                .price(150)
                .type("Comfort")
                .cleaningPrice(40)
                .build();
        room.setNeedsCleaning(false);
        when(roomRepository.findById(205)).thenReturn(Optional.of(room));

        int result = cleaningService.cleanTheRoom(new Maid("NufNuf"), 205, userRepository);

        assertThat(result).isZero();
        verify(roomRepository, never()).save(any());
    }

    @Test
    void testCleanRoom_WhenRoomDoesNotExist() {
        Maid maid = new Maid("Olga");
        when(roomRepository.findById(999)).thenReturn(Optional.empty());

        int result = cleaningService.cleanTheRoom(maid, 999, userRepository);

        assertEquals(-1, result);
        verify(userRepository, never()).save(any());
    }
}