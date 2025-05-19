package testController;

import app.controller.CommentController;
import app.entity.Comment;
import app.entity.Like;
import app.entity.User;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    @DisplayName("POST /comments - cria comentário com sucesso")
    void testCreateComment() throws Exception {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setName("Nome Teste");
        comment.setText("Texto do comentário");

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        String json = "{\"name\":\"Nome Teste\", \"text\":\"Texto do comentário\"}";

        mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nome Teste"))
                .andExpect(jsonPath("$.text").value("Texto do comentário"));
    }

    @Test
    @DisplayName("GET /comments - retorna todos os comentários")
    void testGetAllComments() throws Exception {
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setName("Nome1");
        comment1.setText("Texto1");

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setName("Nome2");
        comment2.setText("Texto2");

        when(commentRepository.findAll()).thenReturn(List.of(comment1, comment2));

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Nome1"))
                .andExpect(jsonPath("$[0].text").value("Texto1"))
                .andExpect(jsonPath("$[1].name").value("Nome2"))
                .andExpect(jsonPath("$[1].text").value("Texto2"));
    }

    @Test
    @DisplayName("POST /comments/{commentId}/likes - curte comentário com sucesso")
    void testLikeComment() throws Exception {
        Long userId = 1L;
        Long commentId = 1L;

        User user = new User();
        user.setId(userId);

        Comment comment = new Comment();
        comment.setId(commentId);

        Like like = new Like();
        like.setId(1L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(likeRepository.save(any(Like.class))).thenReturn(like);

        mockMvc.perform(post("/comments/{commentId}/likes?userId=1", commentId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

//    @Test
// void testLikeCommentUserNotFound() throws Exception {
//     Long userId = 999L;
//     Long commentId = 1L;

//     when(userRepository.findById(userId)).thenReturn(Optional.empty());

//     mockMvc.perform(post("/comments/{commentId}/likes", commentId)
//             .param("userId", String.valueOf(userId))
//             .contentType(MediaType.APPLICATION_JSON))
//             .andExpect(status().isInternalServerError()) // 500 esperado
//             .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException))
//             .andExpect(result -> assertEquals("Usuário não encontrado", result.getResolvedException().getMessage()));
// }

// @Test
// void testLikeCommentCommentNotFound() throws Exception {
//     Long userId = 1L;
//     Long commentId = 999L;

//     when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
//     when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

//     mockMvc.perform(post("/comments/{commentId}/likes", commentId)
//             .param("userId", String.valueOf(userId))
//             .contentType(MediaType.APPLICATION_JSON))
//             .andExpect(status().isInternalServerError()) // 500 esperado
//             .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException))
//             .andExpect(result -> assertEquals("Comentário não encontrado", result.getResolvedException().getMessage()));
// }

}
