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
        "/sql/user_data.sql"  // This should be a SQL script to pre-load test data
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



//package bg.tasky.TaskManagement.tests.repositories;
//
//import bg.tasky.TaskManagement.entities.UserEntity;
//import bg.tasky.TaskManagement.repositories.UserRepo;
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
//@Sql({"/sql/user_data.sql"})
//class UserRepoTest {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Test
//    void testFindUserById() {
//        UserEntity user = userRepo.findUserById(1L);
//        assertThat(user.getFirstName()).isEqualTo("John");
//        assertThat(user.getUsername()).isEqualTo("john_doe");
//    }
//
//    @Test
//    void testFindUserByUsernameSuccess() {
//        UserEntity user = userRepo.findUserByUsername("jane_smith");
//        assertThat(user).isNotNull();
//        assertThat(user.getFirstName()).isEqualTo("Jane");
//    }
//
//    @Test
//    void testFindUserByUsernameOptional() {
//        Optional<UserEntity> result = userRepo.findByUsername("john_doe");
//        assertThat(result).isPresent();
//        assertThat(result.get().getLastName()).isEqualTo("Doe");
//    }
//
//    @Test
//    void testDeleteUserByUsername() {
//        Long deletedCount = userRepo.deleteUserByUsername("john_doe");
//        assertThat(deletedCount).isEqualTo(1L);
//    }
//}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//package bg.tasky.TaskManagement.tests.repositories;
//
//import bg.tasky.TaskManagement.entities.UserEntity;
//import bg.tasky.TaskManagement.repositories.UserRepo;
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
//@Sql({
//        "/sql/test_data.sql"  // Ensure your test data SQL file is properly referenced
//})
//class UserRepoTest {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Test
//    void testFindUserByIdSuccess() {
//        // Arrange
//        Long userId = 1L;  // Assuming the ID 1 exists in your test data
//
//        // Act
//        UserEntity result = userRepo.findUserById(userId);
//
//        // Assert
//        assertThat(result).isNotNull();
//        assertThat(result.getUsername()).isEqualTo("johndoe");
//    }
//
//    @Test
//    void testFindUserByUsernameSuccess() {
//        // Arrange
//        String username = "janedoe"; // Assuming this username exists in your test data
//
//        // Act
//        UserEntity result = userRepo.findUserByUsername(username);
//
//        // Assert
//        assertThat(result).isNotNull();
//        assertThat(result.getFirstName()).isEqualTo("Jane");
//    }
//
//    @Test
//    void testFindUserByUsernameNotFound() {
//        // Arrange
//        String username = "nonexistent"; // Username that does not exist
//
//        // Act
//        UserEntity result = userRepo.findUserByUsername(username);
//
//        // Assert
//        assertThat(result).isNull();
//    }
//
//    @Test
//    void testDeleteUserByUsernameSuccess() {
//        // Arrange
//        String username = "johndoe"; // Assuming this username exists in your test data
//
//        // Act
//        Long deletedCount = userRepo.deleteUserByUsername(username);
//
//        // Assert
//        assertThat(deletedCount).isGreaterThan(0);
//
//        // Verify the user is deleted
//        UserEntity result = userRepo.findUserByUsername(username);
//        assertThat(result).isNull();
//    }
//
//    @Test
//    void testDeleteUserByUsernameNotFound() {
//        // Arrange
//        String username = "nonexistent"; // Username that does not exist
//
//        // Act
//        Long deletedCount = userRepo.deleteUserByUsername(username);
//
//        // Assert
//        assertThat(deletedCount).isEqualTo(0); // Should return 0 if no user was deleted
//    }
//
//    @Test
//    void testFindByUsernameSuccess() {
//        // Arrange
//        String username = "johndoe"; // Assuming this username exists in your test data
//
//        // Act
//        Optional<UserEntity> result = userRepo.findByUsername(username);
//
//        // Assert
//        assertThat(result).isPresent();
//        assertThat(result.get().getFirstName()).isEqualTo("John");
//    }
//
//    @Test
//    void testFindByUsernameNotFound() {
//        // Arrange
//        String username = "nonexistent"; // Username that does not exist
//
//        // Act
//        Optional<UserEntity> result = userRepo.findByUsername(username);
//
//        // Assert
//        assertThat(result).isNotPresent();
//    }
//}

