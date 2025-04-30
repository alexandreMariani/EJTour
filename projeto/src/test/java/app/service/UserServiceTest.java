package app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import app.entity.Role;
import app.entity.User;
import app.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private Validator validator;
    private Role role;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        userService = new UserService(userRepository, validator);
    }

    @Test
@DisplayName("Cena 01 - Validando campos obrigatórios")
void cenario01() {
    User user = new User("", "emailInvalido", null, "123", new Role (null));

    assertThrows(ConstraintViolationException.class, () -> userService.postMapping(user));
}

@Test
@DisplayName("Cena 02 - Cadastro de usuário válido")
void cenario02() {
    User user = new User("Maria", "maria@example.com", LocalDate.of(1990, 1, 1), "senhaSegura", new Role (null));
    when(userRepository.save(any(User.class))).thenReturn(user);
    User saved = userService.postMapping(user);
    assertEquals("Maria", saved.getName());
}

@Test
@DisplayName("Cena 03 - Cadastro com e-mail duplicado")
void cenario03() {
    User user = new User("João", "joao@example.com", LocalDate.of(1995, 2, 2), "senhaSegura", new Role(null));
    when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Email duplicado"));
    assertThrows(RuntimeException.class, () -> userService.postMapping(user));
}

@Test
@DisplayName("Cena 04 - Buscar todos os usuários")
void cenario04() {
    List<User> users = List.of(
        new User("Ana", "ana@example.com", LocalDate.of(1990, 3, 3), "senha123", new Role (null))
    );

    when(userRepository.findAll()).thenReturn(users);

    List<User> result = userService.findAll();
    assertEquals(1, result.size());
}

@Test
@DisplayName("Cena 05 - Buscar por ID existente")
void cenario05() {
    User user = new User("Carlos", "carlos@example.com", LocalDate.of(1980, 4, 4), "segura123", new Role (null));
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    User result = userService.findById(1L);
    assertEquals("Carlos", result.getName());
}

@Test
@DisplayName("Cena 06 - Buscar por ID inexistente")
void cenario06() {
    when(userRepository.findById(999L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.findById(999L));
}

@Test
@DisplayName("Cena 07 - Buscar por e-mail existente")
void cenario07() {
    User user = new User("Julia", "julia@example.com", LocalDate.of(1988, 8, 8), "senhaTop", new Role (null));
    when(userRepository.findByEmail("julia@example.com")).thenReturn(Optional.of(user));

    User result = userService.findByEmail("julia@example.com");
    assertEquals("Julia", result.getName());
}

@Test
@DisplayName("Cena 08 - Buscar por e-mail inexistente")
void cenario08() {
    when(userRepository.findByEmail("nao@existe.com")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.findByEmail("nao@existe.com"));
}

@Test
@DisplayName("Cena 09 - Buscar por nome que contém")
void cenario09() {
    List<User> users = List.of(
        new User("Paulo", "paulo@example.com", LocalDate.of(1975, 7, 7), "senha123", new Role (null))
    );
    when(userRepository.findByNameContains("Paul")).thenReturn(users);

    List<User> result = userService.getUsersByName("Paul");
    assertFalse(result.isEmpty());
}

@Test
@DisplayName("Cena 10 - Atualizar usuário existente")
void cenario10() {
    User user = new User("Lucia", "lucia@example.com", LocalDate.of(1991, 9, 9), "senhaAtual", new Role (null));
    when(userRepository.save(any(User.class))).thenReturn(user);

    User result = userService.putMapping(user);
    assertEquals("Lucia", result.getName());
}

@Test
@DisplayName("Cena 11 - Excluir usuário existente")
void cenario11() {
    when(userRepository.existsById(1L)).thenReturn(true);

    ResponseEntity<Void> response = userService.deleteMapping(1L);
    assertEquals(204, response.getStatusCodeValue());
}

@Test
@DisplayName("Cena 12 - Excluir usuário inexistente")
void cenario12() {
    when(userRepository.existsById(999L)).thenReturn(false);

    ResponseEntity<Void> response = userService.deleteMapping(999L);
    assertEquals(404, response.getStatusCodeValue());
}

}

