package testService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import app.entity.Comment;
import app.entity.Like;
import app.entity.User;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.UserRepository;
import app.service.CommentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testCreateComment() {
        Comment comment = new Comment();
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.createComment(comment);

        assertEquals(comment, result);
    }

    @Test
    void testGetAllComments() {
        Comment comment = new Comment();
        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment));

        List<Comment> result = commentService.getAllComments();

        assertEquals(1, result.size());
    }

    // @Test
    // void testLikeComment() {
    //     User user = new User();
    //     Comment comment = new Comment();
    //     Like like = new Like();

    //     org.mockito.Mockito.lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    //     org.mockito.Mockito.lenient().when(commentRepository.findById(2L)).thenReturn(Optional.of(comment));
    //     org.mockito.Mockito.lenient().when(likeRepository.save(org.mockito.Mockito.any())).thenReturn(like);

    //     Like result = commentService.likeComment(1L, 2L);

    //     assertEquals(like, result);
    // }

    @Test
    void testLikeCommentUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentService.likeComment(1L, 2L));
    }

    @Test
    void testLikeCommentCommentNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(commentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commentService.likeComment(1L, 2L));
    }
}
