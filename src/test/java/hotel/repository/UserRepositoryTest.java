package hotel.repository;

import hotel.entities.Guest;
import hotel.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByName() {
        User guest = new Guest("Sofia");
        userRepository.save(guest);

        Optional<User> found = userRepository.findByName("Sofia");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Sofia");
    }
}