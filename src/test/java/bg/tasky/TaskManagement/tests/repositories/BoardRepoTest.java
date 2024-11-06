//package bg.tasky.TaskManagement.tests.repositories;
//
//import bg.tasky.TaskManagement.entities.BoardEntity;
//import bg.tasky.TaskManagement.repositories.BoardRepo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Sql({"/sql/user_data.sql", "/sql/board_data.sql"}) // SQL script to set up data
//class BoardRepoTest {
//
//    @Autowired
//    private BoardRepo boardRepo;
//
//    @Test
//    void testFindByKeySuccess() {
//        Optional<BoardEntity> boardOptional = boardRepo.findByKey("key1");
//        assertThat(boardOptional).isPresent(); // Check if the board is present
//        assertThat(boardOptional.get().getTitle()).isEqualTo("Board 1"); // Validate title
//    }
//
//    @Test
//    void testFindByKeyNotFound() {
//        Optional<BoardEntity> boardOptional = boardRepo.findByKey("non_existing_key");
//        assertThat(boardOptional).isNotPresent(); // Check that no board is found
//    }
//}


//package bg.tasky.TaskManagement.tests.repositories;
//
//import bg.tasky.TaskManagement.entities.BoardEntity;
//import bg.tasky.TaskManagement.repositories.BoardRepo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Sql("/sql/test_data.sql") // Load test data from the SQL file
//class BoardRepoTest {
//
//    @Autowired
//    private BoardRepo boardRepo;
//
//    @Test
//    void testFindByKey() {
//        Optional<BoardEntity> board = boardRepo.findByKey("personal_board");
//        assertThat(board).isPresent();
//        assertThat(board.get().getTitle()).isEqualTo("Personal Board");
//    }
//
//    @Test
//    void testFindByKeyWhenNotFound() {
//        Optional<BoardEntity> board = boardRepo.findByKey("nonexistent");
//        assertThat(board).isNotPresent();
//    }
//}


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
        "/sql/board_data.sql"  // This SQL script should pre-load test data for BoardEntity
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

//package bg.tasky.TaskManagement.tests.repositories;
//import bg.tasky.TaskManagement.entities.BoardEntity;
//import bg.tasky.TaskManagement.repositories.BoardRepo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.jdbc.Sql;
//import java.util.Optional;
//import static org.assertj.core.api.Assertions.assertThat;
//@DataJpaTest
//@Sql({
//        "/sql/board_data.sql"
//})
//class BoardRepoTest {
//    @Autowired
//    private BoardRepo boardRepo;
//    @Test
//    void testFindEmployeeByEmployeeNumSuccess() {
//        Optional<BoardEntity> result = boardRepo.findByKey("randomKey");
//        assertThat(result.get().getTitle())
//                .isNotNull()
//                .isEqualTo("board1");
//    }
//}
