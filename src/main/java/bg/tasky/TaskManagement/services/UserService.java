package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }
}
