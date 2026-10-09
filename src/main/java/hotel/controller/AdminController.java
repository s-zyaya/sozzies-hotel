package hotel.controller;

import hotel.entities.Maid;
import hotel.entities.Receipt;
import hotel.entities.Room;
import hotel.entities.User;
import hotel.repository.ReceiptRepository;
import hotel.repository.RoomRepository;
import hotel.repository.UserRepository;
import hotel.service.HotelRoomManager;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final HotelRoomManager roomManager;
    private final UserRepository userRepository;
    private final ReceiptRepository receiptRepository;
    private final RoomRepository roomRepository;

    public AdminController(HotelRoomManager roomManager, UserRepository userRepository,
                           ReceiptRepository receiptRepository, RoomRepository roomRepository) {
        this.roomManager = roomManager;
        this.userRepository = userRepository;
        this.receiptRepository = receiptRepository;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/rooms")
    public String addRoom(@RequestParam int num, @RequestParam int price) {
        Room room = new Room.Builder()
                .number(num)
                .price(price)
                .type("LUX")
                .cleaningPrice(100)
                .build();

        roomRepository.save(room);
        return "Кімнату створено!";
    }

    @DeleteMapping("/rooms/{num}")
    public String deleteRoom(@PathVariable int num) {
        boolean deleted = roomManager.deleteRoom(num);
        return deleted ? "Кімнату №" + num + " видалено" : "Неможливо видалити: кімната зайнята або не знайдена";
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/maids")
    public List<User> getMaids() {
        return userRepository.findAll().stream().filter(u -> u instanceof Maid).toList();
    }

    @GetMapping("/history")
    public List<Receipt> getHistory() {
        return receiptRepository.findAll();
    }
}