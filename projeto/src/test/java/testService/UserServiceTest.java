package testService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import app.entity.User;
import app.repository.UserRepository;
import app.service.UserService;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Unit Test - FindAll: deve retornar lista de usuários")
    void testFindAll() {
        User user = new User();
        List<User> mockList = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(mockList);

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Unit Test - FindById: deve retornar usuário pelo id")
    void testFindById() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Unit Test - FindById: deve lançar exceção se usuário não encontrado")
    void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findById(1L));
    }

    @Test
    @DisplayName("Unit Test - FindByEmail: deve retornar usuário pelo e-mail")
    void testFindByEmail() {
        User user = new User();
        user.setEmail("email@example.com");

        when(userRepository.findByEmail("email@example.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("email@example.com");

        assertEquals("email@example.com", result.getEmail());
    }

}
