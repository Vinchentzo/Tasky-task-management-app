package bg.tasky.TaskManagement.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "cards")
@Data
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private ListEntity list;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private Set<TaskEntity> tasks;
}
