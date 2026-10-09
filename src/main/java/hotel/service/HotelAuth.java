package hotel.service;
import hotel.repository.UserRepository;
import hotel.entities.Guest;
import hotel.entities.Maid;
import org.springframework.stereotype.Service;

@Service
public class HotelAuth {
    private final UserRepository userRepository;
    public HotelAuth(UserRepository userRepository) { this.userRepository = userRepository; }

    public boolean adminPasswordCheck(String inputPassword) { return "ziaia222".equals(inputPassword); }

    public Guest guestCheck(String name) {
        return userRepository.findByName(name).filter(u -> u instanceof Guest).map(u -> (Guest) u).orElse(null);
    }

    public Maid maidCheck(String name) {
        return userRepository.findByName(name).filter(u -> u instanceof Maid).map(u -> (Maid) u).orElse(null);
    }
}