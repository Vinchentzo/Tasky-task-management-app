package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
}
