// package integration;

// import app.controller.UserController;
// import app.entity.Role;
// import app.entity.User;
// import app.service.UserService;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.security.test.context.support.WithMockUser;

// import java.time.LocalDate;
// import java.util.List;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// /**
//  * Testes de integração isolada (MockMvc + mocks) para UserController.
//  */
// @ExtendWith(MockitoExtension.class)
// @DisplayName("Integração – UserController")
// class UserControllerIntegrationTest {

//     private MockMvc mockMvc;

//     @Mock
//     private UserService userService;

//     @InjectMocks
//     private UserController userController;

//     private User mockUser;

//     @BeforeEach
//     void setUp() {
//         mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

//         Role adminRole = new Role();
//         adminRole.setId(1L);
//         adminRole.setName("admin");

//         mockUser = new User();
//         mockUser.setId(1L);
//         mockUser.setName("Mock User");
//         mockUser.setEmail("mock@example.com");
//         mockUser.setPassword("$2a$10$hash");          // senha já cifrada
//         mockUser.setBirthday(LocalDate.of(1990, 1, 1));
//         mockUser.setRole(adminRole);
//     }

//     /* ----------- cenários que exigem ROLE_admin ------------- */

//     @Nested
//     @WithMockUser(username = "admin@example.com", roles = "admin")
//     class EndpointsAdmin {

//         @Test
//         @DisplayName("Integração – GET /users/{id} retorna usuário existente")
//         void testFindById() throws Exception {
//             when(userService.findById(1L)).thenReturn(mockUser);

//             mockMvc.perform(get("/users/{id}", 1L))
//                     .andExpect(status().isOk())
//                     .andExpect(jsonPath("$.id").value(1L))
//                     .andExpect(jsonPath("$.name").value("Mock User"));
//         }

//         @Test
//         @DisplayName("Integração – GET /users lista todos usuários")
//         void testFindAll() throws Exception {
//             when(userService.findAll()).thenReturn(List.of(mockUser));

//             mockMvc.perform(get("/users"))
//                     .andExpect(status().isOk())
//                     .andExpect(jsonPath("$[0].email").value("mock@example.com"));
//         }

//         @Test
//         @DisplayName("Integração – POST /users cria usuário")
//         void testSave() throws Exception {
//             when(userService.postMapping(any(User.class))).thenReturn(mockUser);

//             mockMvc.perform(post("/users")
//                             .contentType(MediaType.APPLICATION_JSON)
//                             .content("""
//                                     {
//                                       "name":"Mock User",
//                                       "email":"mock@example.com",
//                                       "password":"plainPwd",
//                                       "birthday":"01-01-1990"
//                                     }
//                                     """))
//                     .andExpect(status().isCreated())
//                     .andExpect(jsonPath("$.email").value("mock@example.com"));
//         }

//         @Test
//         @DisplayName("Integração – PUT /users atualiza usuário")
//         void testEdit() throws Exception {
//             User updated = new User();
//             updated.setId(1L);
//             updated.setName("Updated");
//             updated.setEmail("mock@example.com");
//             updated.setPassword(mockUser.getPassword());
//             updated.setRole(mockUser.getRole());

//             when(userService.putMapping(any(User.class))).thenReturn(updated);

//             mockMvc.perform(put("/users")
//                             .contentType(MediaType.APPLICATION_JSON)
//                             .content("""
//                                     {
//                                       "id":1,
//                                       "name":"Updated"
//                                     }
//                                     """))
//                     .andExpect(status().isOk())
//                     .andExpect(jsonPath("$.name").value("Updated"));
//         }

//         @Test
//         @DisplayName("Integração – DELETE /users/{id} remove usuário")
//         void testDelete() throws Exception {
//             when(userService.deleteMapping(1L)).thenReturn(ResponseEntity.noContent().build());

//             mockMvc.perform(delete("/users/{id}", 1L))
//                     .andExpect(status().isNoContent())
//                     .andExpect(content().string("User deleted successfully"));
//         }
//     }

//     /* ----------- cenários públicos / sem role ------------- */

//     @Test
//     @DisplayName("Integração – POST /users/login autentica com credenciais válidas")
//     void testLogin() throws Exception {
//         when(userService.findByEmail("mock@example.com")).thenReturn(mockUser);

//         mockMvc.perform(post("/users/login")
//                         .param("email", "mock@example.com")
//                         .param("password", "$2a$10$hash"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.email").value("mock@example.com"));
//     }

//     @Test
//     @DisplayName("Integração – POST /users/login falha com credenciais inválidas")
//     void testLoginUnauthorized() throws Exception {
//         when(userService.findByEmail("wrong@example.com")).thenReturn(null);

//         mockMvc.perform(post("/users/login")
//                         .param("email", "wrong@example.com")
//                         .param("password", "badPwd"))
//                 .andExpect(status().isUnauthorized())
//                 .andExpect(content().string("Email ou senha incorretos"));
//     }
// }
