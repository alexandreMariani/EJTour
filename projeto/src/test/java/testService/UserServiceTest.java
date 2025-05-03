import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import app.entity.User;
import app.repository.UserRepository;
import app.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindAll() {
        User user = new User();
        List<User> mockList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(mockList);

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void testPostAndPutMapping() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.postMapping(user);
        User updated = userService.putMapping(user);

        assertEquals(user, saved);
        assertEquals(user, updated);
    }

    @Test
    void testFindById() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals(user, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findById(1L));
    }
}
