package bg.tasky.TaskManagement.tests.repositories;

import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.repositories.BoardRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({
        "/sql/board_data.sql"
})
class BoardRepoTest {

    @Autowired
    private BoardRepo boardRepo;

    @Test
    void testFindByKeySuccess() {
        String testKey = "board123";
        Optional<BoardEntity> result = boardRepo.findByKey(testKey);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Project A");
    }
}

