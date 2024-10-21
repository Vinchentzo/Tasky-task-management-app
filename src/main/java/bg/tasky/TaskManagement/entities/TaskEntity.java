package bg.tasky.TaskManagement.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed")
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardEntity card;
}