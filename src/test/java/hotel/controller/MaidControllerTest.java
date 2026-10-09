package hotel.controller;

import hotel.entities.Maid;
import hotel.entities.Room;
import hotel.service.CleaningService;
import hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaidController.class)
class MaidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CleaningService cleaningService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getDirtyRooms_ShouldReturnList() throws Exception {
        Room dirtyRoom = new Room.Builder().number(305).build();
        when(cleaningService.getRoomsNeedsCleaning()).thenReturn(Arrays.asList(dirtyRoom));

        mockMvc.perform(get("/api/maids/dirty-rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(305));
    }

    @Test
    void cleanRoom_Success() throws Exception {
        String name = "Olga";
        Maid maid = new Maid(name);
        when(userRepository.findByName(name)).thenReturn(Optional.of(maid));
        when(cleaningService.cleanTheRoom(eq(maid), eq(101), any())).thenReturn(1);

        mockMvc.perform(post("/api/maids/clean/101").param("maidName", name))
                .andExpect(status().isOk())
                .andExpect(content().string("Кімнату №101 прибрано. Покоївці Olga нараховано оплату."));
    }

    @Test
    void cleanRoom_NotNeeded() throws Exception {
        String name = "Olga";
        Maid maid = new Maid(name);
        when(userRepository.findByName(name)).thenReturn(Optional.of(maid));
        when(cleaningService.cleanTheRoom(eq(maid), eq(101), any())).thenReturn(0);

        mockMvc.perform(post("/api/maids/clean/101").param("maidName", name))
                .andExpect(status().isOk())
                .andExpect(content().string("Кімната не потребує прибирання."));
    }

    @Test
    void cleanRoom_RoomNotFound() throws Exception {
        String name = "Olga";
        Maid maid = new Maid(name);
        when(userRepository.findByName(name)).thenReturn(Optional.of(maid));
        when(cleaningService.cleanTheRoom(eq(maid), eq(101), any())).thenReturn(-1);

        mockMvc.perform(post("/api/maids/clean/101").param("maidName", name))
                .andExpect(status().isOk())
                .andExpect(content().string("Кімнату не знайдено."));
    }

    @Test
    void cleanRoom_MaidNotFound() throws Exception {
        when(userRepository.findByName("Unknown")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/maids/clean/101").param("maidName", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(content().string("Покоївку з ім'ям Unknown не знайдено."));
    }
}