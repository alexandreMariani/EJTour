// package integration;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import app.ProjetoApplication;
// import app.Security.JwtServiceGenerator;

// import app.Auth.Login;
// import app.Auth.LoginController;
// import app.Auth.LoginService;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;


// @SpringBootTest(classes = ProjetoApplication.class)
// @AutoConfigureMockMvc
// class LoginControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;


//     @MockBean
//     private JwtServiceGenerator jwtServiceGenerator;

//     private final ObjectMapper mapper = new ObjectMapper();

//     @Test
//     @DisplayName("Integração – fluxo completo de login retorna token")
//     void deveRetornarTokenComCredenciaisValidas() throws Exception {
//         when(jwtServiceGenerator.generateToken(any())).thenReturn("jwt-token-integration");

//         Login login = new Login();

//         login.setPassword("123456");
//         login.setUsername("admin@teste.com");

//         mockMvc.perform(post("/user/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(login)))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("jwt-token-integration"));
//     }
// }
