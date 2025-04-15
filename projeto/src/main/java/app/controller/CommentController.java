package app.controller;

import app.entity.Comment;
import app.entity.Like;
import app.entity.User;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
  origins = "*",
  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
  allowedHeaders = "*"
)
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam Long userId, @RequestBody String content) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Comment comment = new Comment(content, user);
            Comment savedComment = commentRepository.save(comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{commentId}/likes")
    public ResponseEntity<?> likeComment(@RequestParam Long userId, @PathVariable Long commentId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
            Like like = new Like();
            like.setUser(user);
            like.setComment(comment);
            Like savedLike = likeRepository.save(like);
            return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error liking comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComments() {
        try {
            List<Comment> comments = commentRepository.findAll();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching comments: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
