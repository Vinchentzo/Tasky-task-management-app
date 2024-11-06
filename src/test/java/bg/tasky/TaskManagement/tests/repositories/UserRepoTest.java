package bg.tasky.TaskManagement.tests.repositories;


import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({
        "/sql/user_data.sql"
})
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    void testFindUserByIdSuccess() {
        Long testId = 1L;
        UserEntity result = userRepo.findUserById(testId);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    @Test
    void testDeleteUserByUsernameSuccess() {
        String username = "testuser";
        Long deletedCount = userRepo.deleteUserByUsername(username);
        assertThat(deletedCount).isEqualTo(1);

        // Ensure that the user no longer exists in the database
        Optional<UserEntity> result = userRepo.findByUsername(username);
        assertThat(result).isEmpty();
    }

    @Test
    void testFindUserByUsernameSuccess() {
        String username = "testuser";
        UserEntity result = userRepo.findUserByUsername(username);
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindByUsernameSuccess() {
        String username = "testuser";
        Optional<UserEntity> result = userRepo.findByUsername(username);
        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("John");
        assertThat(result.get().getLastName()).isEqualTo("Doe");
    }
}