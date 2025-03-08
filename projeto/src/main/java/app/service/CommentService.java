package app.service;

import app.entity.Comment;
import app.entity.Like;
import app.entity.User;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    // Criar um novo comentário
    public Comment createComment(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = new Comment(content, user);
        return commentRepository.save(comment);
    }

    // Criar um "like" para um comentário
    public Like likeComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        return likeRepository.save(like);
    }

    // Listar todos os comentários
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
