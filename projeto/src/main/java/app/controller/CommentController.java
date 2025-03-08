package app.controller;

import app.entity.Comment;
import app.entity.Like;
import app.entity.User;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Comment createComment(@RequestParam Long userId, @RequestBody String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = new Comment(content, user);
        return commentRepository.save(comment);
    }

    // Criar um "like" para um comentário
    @PostMapping("/{commentId}/likes")
    public Like likeComment(@RequestParam Long userId, @PathVariable Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        return likeRepository.save(like);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
