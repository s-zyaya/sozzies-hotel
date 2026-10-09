package hotel.controller;

import hotel.entities.Maid;
import hotel.entities.Room;
import hotel.entities.User;
import hotel.repository.ReceiptRepository;
import hotel.repository.RoomRepository;
import hotel.repository.UserRepository;
import hotel.service.HotelRoomManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelRoomManager roomManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ReceiptRepository receiptRepository; // ЦЬОГО НЕ ВИСТАЧАЛО

    @Test
    void addRoom_ShouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(post("/api/admin/rooms")
                        .param("num", "101")
                        .param("price", "500"))
                .andExpect(status().isOk())
                .andExpect(content().string("Кімнату створено!"));

        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void getMaids_ShouldReturnOnlyMaids() throws Exception {
        User maid = new Maid("Anna");
        User guest = new hotel.entities.Guest("Ivan");
        when(userRepository.findAll()).thenReturn(Arrays.asList(maid, guest));

        mockMvc.perform(get("/api/admin/maids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Anna"));
    }
    @Test
    void deleteRoom_WhenRoomExists_ShouldReturnSuccess() throws Exception {
        when(roomManager.deleteRoom(101)).thenReturn(true);

        mockMvc.perform(delete("/api/admin/rooms/101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Кімнату №101 видалено"));
    }

    @Test
    void deleteRoom_WhenRoomBusyOrNotFound_ShouldReturnErrorMessage() throws Exception {
        when(roomManager.deleteRoom(999)).thenReturn(false);

        mockMvc.perform(delete("/api/admin/rooms/999"))
                .andExpect(status().isOk())
                .andExpect(content().string("Неможливо видалити: кімната зайнята або не знайдена"));
    }

    @Test
    void getMaids_WhenNoMaidsExist_ShouldReturnEmptyList() throws Exception {
        User guest = new hotel.entities.Guest("Sofia");
        when(userRepository.findAll()).thenReturn(Arrays.asList(guest));

        mockMvc.perform(get("/api/admin/maids"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Перевіряємо фільтрацію stream().filter()
    }

    @Test
    void getAllRooms_ShouldReturnListFromAdminPanel() throws Exception {
        Room room = new Room.Builder().number(101).build();
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/api/admin/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(101));
    }
}