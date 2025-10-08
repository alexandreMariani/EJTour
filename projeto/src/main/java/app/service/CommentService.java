package app.service;

import app.entity.Comment;
import app.entity.Like;
import app.entity.AppUser;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.AppUserRepository;
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
    private AppUserRepository AppUserRepository;


    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Like likeComment(Long AppUserId, Long commentId) {
        AppUser AppUser = AppUserRepository.findById(AppUserId)
                .orElseThrow(() -> new RuntimeException("AppUser not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Like like = new Like();
        return likeRepository.save(like);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
