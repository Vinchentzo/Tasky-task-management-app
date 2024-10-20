package bg.tasky.TaskManagement.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "lists")
@Data
public class ListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
    private Set<CardEntity> cards;
}
