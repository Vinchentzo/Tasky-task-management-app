package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListRepo extends JpaRepository<ListEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM ListEntity l WHERE l.title = :title AND l.board.key = :boardKey")
    public boolean existsByTitleAndBoardKey(@Param("title") String title, @Param("boardKey") String boardKey);
    public ListEntity findByTitleAndBoardKey(String title, String boardKey);
    public List<ListEntity> findAllByBoardKey(String boardKey);
}
