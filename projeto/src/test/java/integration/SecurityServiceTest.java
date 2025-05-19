// package integration;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import app.Auth.Login;

// @SpringBootTest
// @AutoConfigureMockMvc
// class SecurityConfigWebTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper mapper;         

//     @Test
//     @DisplayName("Integração – rota pública /user/login responde 200 sem token")
//     void rotaLoginEhPublica() throws Exception {

//         Login body = new Login();
//         body.setUsername("usuario@mail.com");
//         body.setPassword("usuario");

//         mockMvc.perform(post("/user/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(body)))
//                .andExpect(status().isOk());
//     }

//     @Test
//     @DisplayName("Integração – rota /users exige autenticação e retorna 403 sem token")
//     void rotaUsersProtegida() throws Exception {
//         mockMvc.perform(get("/users"))
//                .andExpect(status().isForbidden());
//     }
// }