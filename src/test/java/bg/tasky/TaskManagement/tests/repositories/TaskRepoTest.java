package bg.tasky.TaskManagement.tests.repositories;

import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.entities.TaskEntity;
import bg.tasky.TaskManagement.repositories.CardRepo;
import bg.tasky.TaskManagement.repositories.ListRepo;
import bg.tasky.TaskManagement.repositories.TaskRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/sql/task_data.sql"})
class TaskRepoTest {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private ListRepo listRepo;

    @Test
    void testExistsByTitleAndCard() {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        assertThat(listEntity).isNotNull();

        CardEntity cardEntity = cardRepo.findByTitleAndList("Setup environment", listEntity);
        assertThat(cardEntity).isNotNull();

        boolean exists = taskRepo.existsByTitleAndCard("Install IDE", cardEntity);
        assertThat(exists).isTrue();
    }

    @Test
    void testFindByTitleAndCardSuccess() {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        assertThat(listEntity).isNotNull();

        CardEntity cardEntity = cardRepo.findByTitleAndList("Setup environment", listEntity);
        assertThat(cardEntity).isNotNull();

        TaskEntity task = taskRepo.findByTitleAndCard("Install IDE", cardEntity);
        assertThat(task).isNotNull();
        assertThat(task.getTitle()).isEqualTo("Install IDE");
        assertThat(task.getCompleted()).isFalse();
    }

    @Test
    void testFindAllByCard() {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        assertThat(listEntity).isNotNull();

        CardEntity cardEntity = cardRepo.findByTitleAndList("Setup environment", listEntity);
        assertThat(cardEntity).isNotNull();

        List<TaskEntity> tasks = taskRepo.findAllByCard(cardEntity);
        assertThat(tasks).isNotEmpty();
        assertThat(tasks).hasSize(2);
    }
}
