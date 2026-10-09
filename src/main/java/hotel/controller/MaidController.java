package hotel.controller;

import hotel.service.CleaningService;
import hotel.entities.Maid;
import hotel.entities.Room;
import hotel.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/maids")
public class MaidController {

    private final CleaningService cleaningService;
    private final UserRepository userRepository;

    public MaidController(CleaningService cleaningService, UserRepository userRepository) {
        this.cleaningService = cleaningService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dirty-rooms")
    public List<Room> getDirtyRooms() {
        return cleaningService.getRoomsNeedsCleaning();
    }

    @PostMapping("/clean/{roomNum}")
    public String cleanRoom(@RequestParam String maidName, @PathVariable int roomNum) {
        return userRepository.findByName(maidName)
                .filter(u -> u instanceof Maid)
                .map(u -> {
                    Maid maid = (Maid) u;
                    int result = cleaningService.cleanTheRoom(maid, roomNum, userRepository);
                    if (result == 1) return "Кімнату №" + roomNum + " прибрано. Покоївці " + maidName + " нараховано оплату.";
                    if (result == 0) return "Кімната не потребує прибирання.";
                    return "Кімнату не знайдено.";
                }).orElse("Покоївку з ім'ям " + maidName + " не знайдено.");
    }
}