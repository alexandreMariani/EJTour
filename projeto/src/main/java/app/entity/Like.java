package app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"likes", "comments"})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment; 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes; 

    public Like(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}
