package bg.tasky.TaskManagement.tests.repositories;

import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.repositories.CardRepo;
import bg.tasky.TaskManagement.repositories.ListRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({
        "/sql/card_data.sql"  // SQL script to pre-load data for CardEntity and ListEntity
})
class CardRepoTest {

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private ListRepo listRepo;

    @Test
    void testExistsByTitleAndList() {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        String cardTitle = "Setup environment";

        boolean exists = cardRepo.existsByTitleAndList(cardTitle, listEntity);

        assertThat(exists).isTrue();
    }

    @Test
    void testFindByTitleAndListSuccess() {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");
        String cardTitle = "Setup environment";

        CardEntity result = cardRepo.findByTitleAndList(cardTitle, listEntity);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Setup environment");
    }

    @Test
    void testFindAllByList() {
        ListEntity listEntity = listRepo.findByTitleAndBoardKey("Backlog", "projectBoard123");

        List<CardEntity> cards = cardRepo.findAllByList(listEntity);

        assertThat(cards).isNotEmpty();
        assertThat(cards).hasSize(2);  // Adjust based on the test data in card_data.sql
    }
}
