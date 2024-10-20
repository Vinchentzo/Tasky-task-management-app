package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<BoardEntity, Long> {


}
