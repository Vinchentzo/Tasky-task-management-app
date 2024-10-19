package bg.tasky.TaskManagement.repositories;

import bg.tasky.TaskManagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    public UserEntity findUserById(Long id);
    public Long deleteUserByUsername(String username);
    public UserEntity findUserByUsername(String username);
    public Optional<UserEntity> findByUsername(String username);
}

