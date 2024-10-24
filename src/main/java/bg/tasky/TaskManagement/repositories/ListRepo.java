package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListRepo extends JpaRepository<ListEntity, Long> {
//    public boolean existsByTitleAndBoard(String listTitle, BoardEntity board);
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM ListEntity l WHERE l.title = :title AND l.board.key = :boardKey")
    boolean existsByTitleAndBoardKey(@Param("title") String title, @Param("boardKey") String boardKey);
}
