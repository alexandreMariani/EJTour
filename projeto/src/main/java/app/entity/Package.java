package app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "packages")

public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório.")
    private String title;

    @NotBlank(message = "O campo descrição é obrigatório.")
    private String description;

    @ElementCollection
    @CollectionTable(name = "package_activities", joinColumns = @JoinColumn(name = "package_id"))
    @Column(name = "activity")
    private List<String> activides;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Package( String title, String description) {
        this.title = title;
        this.description = description;
    }

}
