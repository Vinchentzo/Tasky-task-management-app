package bg.tasky.TaskManagement.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "boards")
@Data
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "key")
    private String key;

    @ManyToMany(mappedBy = "boards")
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<ListEntity> lists;
}
