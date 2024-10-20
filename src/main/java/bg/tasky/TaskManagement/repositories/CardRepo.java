package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepo extends JpaRepository<CardEntity, Long> {
}
