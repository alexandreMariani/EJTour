package app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserServiceTest {

    @Mock
    private UserService userService; // Mock do UserService

    private Validator validator; // Instância do validador

    public UserServiceTest(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
        // Inicializa o validador para validação explícita
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Cena 01 - Validando os campos obrigatórios")
    void cenario01() {
        User user = new User(null, null, null, null, null);

        // Testando nome vazio
        user.setName("");
        assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            userService.save(user);  // Verificando se o método save dispara exceção
        });

        // Testando e-mail inválido
        user.setName("João");
        user.setEmail("emailInvalido");
        assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            userService.save(user);
        });

        // Testando senha curta
        user.setEmail("joao@example.com");
        user.setPassword("123");
        assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            userService.save(user);
        });

        // Testando data de nascimento nula
        user.setPassword("123456");
        user.setBirthday(null);
        assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            userService.save(user);
        });
    }
}
