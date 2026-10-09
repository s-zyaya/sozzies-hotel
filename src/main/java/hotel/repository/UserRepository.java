package hotel.repository;
import hotel.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE TYPE(u) = Guest")
    List<User> findAllGuests();

    Optional<User> findByName(String name);
}