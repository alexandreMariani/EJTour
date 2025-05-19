package integration;

import app.controller.RoleController;
import app.entity.Role;
import app.service.RoleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Integração – RoleController")
class RoleControllerIntegrationTest {

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
    @DisplayName("Integração – findById deve retornar Role existente")
    void testFindById() throws Exception {
        Role role = new Role();
        role.setId(1L);
        role.setName("Admin");

        when(roleService.findById(1L)).thenReturn(role);

        mockMvc.perform(get("/role/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Admin"));
    }

    @Test
    @DisplayName("Integração – save deve criar nova Role")
    void testSave() throws Exception {
        Role savedRole = new Role();
        savedRole.setId(2L);
        savedRole.setName("User");

        when(roleService.postMapping(any(Role.class))).thenReturn(savedRole);

        mockMvc.perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name":"User"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("User"));
    }
}
