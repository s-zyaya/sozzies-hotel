package hotel.controller;

import hotel.entities.Guest;
import hotel.entities.Room;
import hotel.repository.UserRepository;
import hotel.service.CleaningService;
import hotel.service.ReceptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceptionService receptionService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CleaningService cleaningService;

    @Test
    void checkIn_ShouldReturnSuccess() throws Exception {
        when(receptionService.isRoomAvailable(101)).thenReturn(true);

        mockMvc.perform(post("/api/guests/check-in")
                        .param("name", "Sofia")
                        .param("roomNum", "101")
                        .param("nights", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Гість Sofia успішно оселився в номер 101"));

        verify(receptionService).checkIn(any(Guest.class), eq(101), eq(2));
    }

    @Test
    void callCleaning_ShouldReturnSuccessMessage() throws Exception {
        Guest guest = new Guest("Sofia");
        guest.setRoomNum(101);
        Room room = new Room.Builder().number(101).build();
        room.setNeedsCleaning(false); // Ще не викликано

        when(userRepository.findByName("Sofia")).thenReturn(Optional.of(guest));
        when(cleaningService.checkRoomForCleaningCall(101)).thenReturn(room);

        mockMvc.perform(post("/api/guests/Sofia/call-cleaning"))
                .andExpect(status().isOk())
                .andExpect(content().string("Прибирання викликано для номера 101"));

        verify(cleaningService).confirmCleaningCall(room);
    }

    @Test
    void checkIn_WhenRoomOccupied_ShouldReturnErrorMessage() throws Exception {
        when(receptionService.isRoomAvailable(101)).thenReturn(false);

        mockMvc.perform(post("/api/guests/check-in")
                        .param("name", "Ivan")
                        .param("roomNum", "101")
                        .param("nights", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Номер 101 зайнятий або не існує"));
    }
}