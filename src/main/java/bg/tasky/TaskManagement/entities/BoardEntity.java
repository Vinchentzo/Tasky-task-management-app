package bg.tasky.TaskManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "board_key", unique = true)
    private String key;

//    @ManyToMany(mappedBy = "boards")
//    @JsonIgnore
//    private Set<UserEntity> users;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    @JsonIgnore
    private Set<ListEntity> lists;
}
