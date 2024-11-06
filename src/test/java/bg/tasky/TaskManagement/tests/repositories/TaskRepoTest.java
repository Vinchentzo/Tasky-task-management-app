package bg.tasky.TaskManagement.tests.repositories;

import bg.tasky.TaskManagement.entities.BoardEntity;
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
@Sql({"/sql/task_data.sql"})  // Make sure the correct SQL script is used
class TaskRepoTest {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private ListRepo listRepo;

    @Test
    void testExistsByTitleAndCard() {
        // Step 1: Find the ListEntity first by its title and board key
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        assertThat(listEntity).isNotNull();  // Ensure list exists

        // Step 2: Find the CardEntity associated with that list
        CardEntity cardEntity = cardRepo.findByTitleAndList("Setup environment", listEntity);
        assertThat(cardEntity).isNotNull();  // Ensure card exists

        // Step 3: Check if the task exists for that card
        boolean exists = taskRepo.existsByTitleAndCard("Install IDE", cardEntity);
        assertThat(exists).isTrue();  // Should return true if task exists
    }

    @Test
    void testFindByTitleAndCardSuccess() {
        // Step 1: Find the ListEntity first by its title and board key
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        assertThat(listEntity).isNotNull();  // Ensure list exists

        // Step 2: Find the CardEntity associated with that list
        CardEntity cardEntity = cardRepo.findByTitleAndList("Setup environment", listEntity);
        assertThat(cardEntity).isNotNull();  // Ensure card exists

        // Step 3: Find the TaskEntity for the card
        TaskEntity task = taskRepo.findByTitleAndCard("Install IDE", cardEntity);
        assertThat(task).isNotNull();  // Ensure task exists
        assertThat(task.getTitle()).isEqualTo("Install IDE");
        assertThat(task.getCompleted()).isFalse();
    }

    @Test
    void testFindAllByCard() {
        // Step 1: Find the ListEntity first by its title and board key
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        assertThat(listEntity).isNotNull();  // Ensure list exists

        // Step 2: Find the CardEntity associated with that list
        CardEntity cardEntity = cardRepo.findByTitleAndList("Setup environment", listEntity);
        assertThat(cardEntity).isNotNull();  // Ensure card exists

        // Step 3: Find all tasks related to the card
        List<TaskEntity> tasks = taskRepo.findAllByCard(cardEntity);
        assertThat(tasks).isNotEmpty();  // Ensure tasks exist for the card
        assertThat(tasks).hasSize(2);  // Adjust based on how many tasks are linked to this card
    }
}
