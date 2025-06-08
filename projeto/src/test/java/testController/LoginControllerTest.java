// package testController;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import app.Auth.Login;
// import app.Auth.LoginController;
// import app.Auth.LoginService;
// import app.Security.JwtServiceGenerator;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// @ExtendWith(MockitoExtension.class)
// class LoginControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     /** Mockamos apenas o gerador de token */
//     @MockBean
//     private JwtServiceGenerator jwtServiceGenerator;

//     // @Test
//     // @DisplayName("Integração – fluxo completo de login retorna token")
//     // void deveRetornarTokenComCredenciaisValidas() throws Exception {

//     //     when(jwtServiceGenerator.generateToken(any())).thenReturn("jwt-token-integration");

//     //     // Corpo JSON enviado diretamente – independe de Lombok ou métodos setter
//     //     String loginJson = """
//     //         {
//     //           "username": "admin@teste.com",
//     //           "password": "123456"
//     //         }
//     //         """;

//     //     mockMvc.perform(post("/user/login")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(loginJson))
//     //             .andExpect(status().isOk())
//     //             .andExpect(content().string("jwt-token-integration"));
//     // }
// }
