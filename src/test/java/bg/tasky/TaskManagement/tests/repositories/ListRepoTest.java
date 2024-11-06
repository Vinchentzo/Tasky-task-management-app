package bg.tasky.TaskManagement.tests.repositories;


import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.repositories.ListRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({
        "/sql/list_data.sql"
})
class ListRepoTest {

    @Autowired
    private ListRepo listRepo;

    @Test
    void testExistsByTitleAndBoardKey() {
        String title = "Backlog";
        String boardKey = "projectBoard123";

        boolean exists = listRepo.existsByTitleAndBoardKey(title, boardKey);

        assertThat(exists).isTrue();
    }

    @Test
    void testFindByTitleAndBoardKeySuccess() {
        String title = "In Progress";
        String boardKey = "projectBoard123";

        ListEntity result = listRepo.findByTitleAndBoardKey(title, boardKey);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("In Progress");
    }

    @Test
    void testFindAllByBoardKey() {
        String boardKey = "projectBoard123";

        List<ListEntity> lists = listRepo.findAllByBoardKey(boardKey);

        assertThat(lists).isNotEmpty();
        assertThat(lists).hasSize(2);
    }
}
