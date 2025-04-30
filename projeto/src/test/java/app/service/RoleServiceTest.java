package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.Role;
import app.repository.RoleRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class RoleServiceTest {
    
    @Mock
    private RoleRepository roleRepository;
    private RoleService roleService;
    private Validator validator;
    
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        roleService = new RoleService();
    }

    @Test
    @DisplayName("cena 01 - validando campo obrigatorio")
    void cenario01(){
        Role role = new Role("");

        assertThrows(ConstraintViolationException.class, () -> roleService.postMapping(role));
    }

    @Test
    @DisplayName("cena 02 - Buscar por Id inexistente")
    void cenario02(){
        when(roleRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> roleService.findById(999L));
    }

    @Test
    @DisplayName("cena 03 - Buscar por ID")
    void cenario03(){
        Role role = new Role("admin");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role result = roleService.findById(1L);
        assertEquals("admin", result.getName());
    }
}
