package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.UserMapper;
import bg.tasky.TaskManagement.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepo.findUserById(id);
    }

    public UserEntity createUser(UserDto user) {
        var userEnt = userMapper.convertDtoToEntity(user);

        return userRepo.save(userEnt);
    }
}
