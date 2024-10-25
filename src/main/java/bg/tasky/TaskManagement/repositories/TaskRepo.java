package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
    public boolean existsByTitleAndCard(String taskTitle, CardEntity cardEntity);

    public TaskEntity findByTitleAndCard(String taskTitle, CardEntity cardEntity);

    public List<TaskEntity> findAllByCard(CardEntity cardEntity);
}
