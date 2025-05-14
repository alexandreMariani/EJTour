package integration;

import app.ProjetoApplication;
import app.entity.User;
import app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProjetoApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testFindByIdWithMockito() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Mocked User");
        mockUser.setEmail("mocked@example.com");

        when(userService.findById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mocked User"))
                .andExpect(jsonPath("$.email").value("mocked@example.com"));
    }

    @Test
    void testCreateUser() throws Exception {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("New User");
        mockUser.setEmail("new@example.com");

        when(userService.save(Mockito.any(User.class))).thenReturn(mockUser);

        String userJson = """
            {
                "name": "New User",
                "email": "new@example.com"
            }
        """;

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Updated User");
        updatedUser.setEmail("updated@example.com");

        when(userService.update(Mockito.eq(1L), Mockito.any(User.class))).thenReturn(updatedUser);

        String updatedUserJson = """
            {
                "name": "Updated User",
                "email": "updated@example.com"
            }
        """;

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }
}
