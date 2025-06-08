// package testController;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// import app.controller.UserController;
// import app.entity.User;
// import app.service.UserService;

// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.http.MediaType;

// @ExtendWith(MockitoExtension.class)
// public class UserControllerTest {

//     private MockMvc mockMvc;

//     @Mock
//     private UserService userService;

//     @InjectMocks
//     private UserController userController;

//     @BeforeEach
//     void setUp() {
//         mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//     }

//     @Test
//     @DisplayName("Integration Test - FindById: retorna usuário pelo id com sucesso")
//     void testFindById() throws Exception {
//         User user = new User();
//         user.setId(1L);
//         user.setName("Test User");

//         when(userService.findById(1L)).thenReturn(user);

//         mockMvc.perform(get("/users/{id}", 1L))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.name").value("Test User"));
//     }

//     @Test
//     @DisplayName("Integration Test - FindById: retorna 404 quando usuário não encontrado")
//     void testFindByIdNotFound() throws Exception {
//         when(userService.findById(1L)).thenReturn(null);

//         mockMvc.perform(get("/users/{id}", 1L))
//                 .andExpect(status().isNotFound())
//                 .andExpect(content().string("User not found"));
//     }

//     @Test
//     @DisplayName("Integration Test - Login: autentica usuário com credenciais válidas")
//     void testLogin() throws Exception {
//         User user = new User();
//         user.setEmail("test@example.com");
//         user.setPassword("password");

//         when(userService.findByEmail("test@example.com")).thenReturn(user);

//         mockMvc.perform(post("/users/login")
//                 .param("email", "test@example.com")
//                 .param("password", "password"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.email").value("test@example.com"));
//     }

//     @Test
//     @DisplayName("Integration Test - Login: retorna 401 para credenciais inválidas")
//     void testLoginUnauthorized() throws Exception {
//         when(userService.findByEmail("test@example.com")).thenReturn(null);

//         mockMvc.perform(post("/users/login")
//                 .param("email", "test@example.com")
//                 .param("password", "wrongpassword"))
//                 .andExpect(status().isUnauthorized())
//                 .andExpect(content().string("Email ou senha incorretos"));
//     }

//     @Test
//     @DisplayName("Integration Test - FindAll: retorna lista de todos usuários")
//     void testFindAll() throws Exception {
//         User user1 = new User();
//         user1.setId(1L);
//         user1.setName("User 1");

//         User user2 = new User();
//         user2.setId(2L);
//         user2.setName("User 2");

//         when(userService.findAll()).thenReturn(List.of(user1, user2));

//         mockMvc.perform(get("/users"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].name").value("User 1"))
//                 .andExpect(jsonPath("$[1].name").value("User 2"));
//     }

//     @Test
//     @DisplayName("Integration Test - Save: cria novo usuário com sucesso")
//     void testSave() throws Exception {
//         User user = new User();
//         user.setName("New User");
//         user.setEmail("newuser@example.com");

//         when(userService.postMapping(any(User.class))).thenReturn(user);

//         mockMvc.perform(post("/users")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"name\":\"New User\",\"email\":\"newuser@example.com\"}"))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.name").value("New User"));
//     }

//     // @Test
//     // @DisplayName("Integration Test - Delete: deleta usuário pelo id com sucesso")
//     // void testDelete() throws Exception {
//     //     doNothing().when(userService).deleteMapping(1L);
//     //
//     //     mockMvc.perform(delete("/users/{id}", 1L))
//     //             .andExpect(status().isNoContent());
//     // }

//     @Test
//     @DisplayName("Integration Test - Edit: atualiza dados do usuário com sucesso")
//     void testEdit() throws Exception {
//         User user = new User();
//         user.setId(1L);
//         user.setName("Updated User");

//         when(userService.putMapping(any(User.class))).thenReturn(user);

//         mockMvc.perform(put("/users")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"id\":1,\"name\":\"Updated User\"}"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Updated User"));
//     }
// }
