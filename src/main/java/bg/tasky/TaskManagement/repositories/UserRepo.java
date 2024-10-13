package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findUserByFirstName(String firstName);
}

