package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.Comment;
import app.repository.CommentRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    private CommentService commentService;
    private Validator validator;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        commentService = new CommentService(commentRepository, validator);
    }

    @Test
    @DisplayName("Cena 01 - validando campos obrigatorios ")
    void cenario01(){
        Comment comment = new Comment("", "");
        when(commentRepository.save(comment)).thenReturn(comment);
        assertThrows(ConstraintViolationException.class, () -> commentService.createComment(comment));
    }

    @Test
    @DisplayName("Cena 02 - lista de comentarios")
    void cenario02(){
        List<Comment> comments = List.of(
            new Comment("jean","aiaiaiaiai")
        );

        when(commentRepository.findAll()).thenReturn(comments);
        List<Comment> result = commentService.getAllComments();
        assertEquals(1, result.size());
    }

}
