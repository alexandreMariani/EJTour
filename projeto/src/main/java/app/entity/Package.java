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

    public Package() {}

    public Package(Long id, String title, String description, List<String> activides) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.activides = activides;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getActivides() {
        return activides;
    }

    public void setActivides(List<String> activides) {
        this.activides = activides;
    }
}
