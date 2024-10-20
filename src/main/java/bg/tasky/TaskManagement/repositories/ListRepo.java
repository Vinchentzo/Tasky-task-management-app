package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepo  extends JpaRepository<ListEntity, Long> {
}
