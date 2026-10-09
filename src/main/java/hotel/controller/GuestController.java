package hotel.controller;

import hotel.entities.Guest;
import hotel.entities.Room;
import hotel.entities.User;
import hotel.repository.UserRepository;
import hotel.service.CleaningService;
import hotel.service.ReceptionService;
import hotel.service.CleaningObserver;
import hotel.service.ConsoleCleaningLogger;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final ReceptionService receptionService;
    private final UserRepository userRepository;
    private final CleaningService cleaningService;

    private final CleaningObserver cleaningObserver = new ConsoleCleaningLogger();

    public GuestController(ReceptionService receptionService, UserRepository userRepository, CleaningService cleaningService) {
        this.receptionService = receptionService;
        this.userRepository = userRepository;
        this.cleaningService = cleaningService;
    }

    @GetMapping
    public List<User> getAllGuests() {
        return userRepository.findAll();
    }

    @PostMapping("/check-in")
    public String checkIn(@RequestParam String name, @RequestParam int roomNum, @RequestParam int nights) {
        if (receptionService.isRoomAvailable(roomNum)) {
            Guest guest = new Guest(name);
            receptionService.checkIn(guest, roomNum, nights);
            return "Гість " + name + " успішно оселився в номер " + roomNum;
        }
        return "Номер " + roomNum + " зайнятий або не існує";
    }

    @PostMapping("/{name}/check-out")
    public String checkOut(@PathVariable String name) {
        return userRepository.findByName(name)
                .filter(u -> u instanceof Guest)
                .map(u -> {
                    Guest g = (Guest) u;
                    Room r = receptionService.getGuestRoom(g, g.getRoomNum());
                    if (r != null) {
                        int total = receptionService.calculateFinalPrice(r, g);
                        receptionService.finalizeCheckOut(g, r);
                        return "Виселення успішне. Гість " + name + " сплатив: " + total + "$";
                    }
                    return "Помилка: номер не знайдено";
                }).orElse("Гість з таким ім'ям не знайдений");
    }

    @PostMapping("/{name}/call-cleaning")
    public String callCleaning(@PathVariable String name) {
        return userRepository.findByName(name)
                .filter(u -> u instanceof Guest)
                .map(u -> {
                    Guest g = (Guest) u;
                    Room r = cleaningService.checkRoomForCleaningCall(g.getRoomNum());
                    if (r != null) {
                        if (!r.isNeedsCleaning()) {
                            cleaningService.confirmCleaningCall(r);

                            cleaningObserver.update(g.getRoomNum());

                            return "Прибирання викликано для номера " + g.getRoomNum();
                        }
                        return "Запит на прибирання вже було надіслано раніше.";
                    }
                    return "Помилка: номер не знайдено або він порожній.";
                }).orElse("Гість не знайдений.");
    }
}