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
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
    RequestMethod.OPTIONS }, allowedHeaders = "*")
@RequestMapping("/comments")
public class CommentController {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private LikeRepository likeRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public ResponseEntity<?> createComment(@RequestBody Comment comment) {

    User user = new User();
    user.setId(1L);
    comment.setUser(user);
    Comment savedComment = commentRepository.save(comment);
    return new ResponseEntity<>(savedComment, HttpStatus.CREATED);

  }

  @PostMapping("/{commentId}/likes")
  public ResponseEntity<?> likeComment(
      @RequestParam Long userId,
      @PathVariable Long commentId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

    Like like = new Like();
    like.setUser(user);
    like.setComment(comment);
    Like savedLike = likeRepository.save(like);

    return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<?> getAllComments() {

    List<Comment> comments = commentRepository.findAll();
    return new ResponseEntity<>(comments, HttpStatus.OK);

  }
}
