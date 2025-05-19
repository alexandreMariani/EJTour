package testService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import app.entity.Role;
import app.repository.RoleRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import app.service.RoleService;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    @DisplayName("Unit Test - FindById: deve retornar role pelo id")
    void testFindById() {
        Role role = new Role();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role result = roleService.findById(1L);

        assertEquals(role, result);
    }

    @Test
    @DisplayName("Unit Test - FindById: deve lançar exceção se role não encontrado")
    void testFindByIdNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.findById(1L));
    }

    @Test
    @DisplayName("Unit Test - Save (POST): deve salvar role")
    void testPostMapping() {
        Role role = new Role();
        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.postMapping(role);

        assertEquals(role, result);
    }
}
