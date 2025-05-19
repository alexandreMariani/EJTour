// package integration;

// import app.entity.Comment;
// import app.repository.CommentRepository;
// import app.repository.UserRepository;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.web.server.LocalServerPort;
// import org.springframework.http.*;

// import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @DisplayName("Integração – CommentController")
// public class CommentControllerIntegrationTest {

//     @LocalServerPort
//     private int port;

//     @Autowired
//     private CommentRepository commentRepository;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private TestRestTemplate restTemplate;

//     private String baseUrl;

//     @BeforeEach
//     void setUp() {
//         baseUrl = "http://localhost:" + port + "/comments";
//     }

//     @Test
//     @DisplayName("Deve criar um novo comentário")
//     void testCreateComment() {
//         // Supondo que exista um usuário com ID 1 no banco de dados
//         Comment comment = new Comment();
//         comment.setName("Usuário Teste");
//         comment.setText("Este é um comentário de teste.");

//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);

//         HttpEntity<Comment> request = new HttpEntity<>(comment, headers);

//         ResponseEntity<Comment> response = restTemplate.postForEntity(baseUrl, request, Comment.class);

//         assertEquals(HttpStatus.CREATED, response.getStatusCode());
//         assertNotNull(response.getBody());
//         assertEquals("Usuário Teste", response.getBody().getName());
//     }

//     @Test
//     @DisplayName("Deve retornar todos os comentários")
//     void testGetAllComments() {
//         ResponseEntity<Comment[]> response = restTemplate.getForEntity(baseUrl, Comment[].class);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         assertNotNull(response.getBody());
//     }
// }
