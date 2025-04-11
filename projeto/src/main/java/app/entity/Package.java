package app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
