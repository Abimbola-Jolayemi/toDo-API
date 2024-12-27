package africa.semicolon.toDo.data.repositories;

import africa.semicolon.toDo.data.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void startWith() {
        user = new User();
        user.setEmail("abimbs@gmail.com");
        user.setPassword("0000");
        user.setCreatedAt(LocalDateTime.now());
        user.setName("Abimbola");
    }

    @AfterEach
    void stopWith() {
        userRepository.deleteAll();
    }

    @Test
    void testThatRepositoryIsEmptyWhenNoUserIsAdded() {
        assertEquals(0, userRepository.count());
    }

    @Test
    void testThatRepositoryIsNotEmptyWhenUserIsAdded() {
        User savedUser = userRepository.save(user);
        assertEquals(1, userRepository.count());
    }

    @Test
    void testThatRepositoryCanFindUserByEmail() {
        userRepository.save(user);
        User foundUser = userRepository.findByEmail("abimbs@gmail.com").orElseThrow();
        assertEquals("Abimbola", foundUser.getName());
    }
}
