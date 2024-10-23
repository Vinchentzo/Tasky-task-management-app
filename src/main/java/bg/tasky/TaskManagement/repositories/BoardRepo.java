package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepo extends JpaRepository<BoardEntity, Long> {

    //public List<BoardEntity> findByUsers_Id(Long id);

    public Optional<BoardEntity> findByKey(String key);
}
