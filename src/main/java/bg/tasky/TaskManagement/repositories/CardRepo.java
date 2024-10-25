package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepo extends JpaRepository<CardEntity, Long> {
    public boolean existsByTitleAndList(String cardTitle, ListEntity listEntity);

    public CardEntity findByTitleAndList(String cardTitle, ListEntity listEntity);

    public List<CardEntity> findAllByList(ListEntity listEntity);
}
