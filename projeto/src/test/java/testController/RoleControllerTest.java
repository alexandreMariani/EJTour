package testController;

import app.controller.RoleController;
import app.entity.Role;
import app.service.RoleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    @DisplayName("GET /role/{id} - deve retornar role com sucesso")
    void testFindByIdSuccess() throws Exception {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        when(roleService.findById(1L)).thenReturn(role);

        mockMvc.perform(get("/role/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    @DisplayName("GET /role/{id} - deve retornar 404 se role não encontrado")
    void testFindByIdNotFound() throws Exception {
        when(roleService.findById(1L)).thenThrow(new RuntimeException("permissao não encontrado!"));

        mockMvc.perform(get("/role/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /role - deve criar role com sucesso")
    void testSaveRole() throws Exception {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        when(roleService.postMapping(any(Role.class))).thenReturn(role);

        String json = "{\"name\":\"USER\"}";

        mockMvc.perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("USER"));
    }
}
