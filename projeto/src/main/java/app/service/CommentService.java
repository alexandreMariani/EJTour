package app.service;

import app.entity.Comment;
import app.entity.Like;
import app.entity.User;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.UserRepository;
import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;
    private UserRepository userRepository;
    private Validator validator;

    public CommentService(CommentRepository commentRepository, Validator validator) {
        this.commentRepository = commentRepository;
        this.validator = validator;
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Like likeComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Like like = new Like();
        return likeRepository.save(like);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
