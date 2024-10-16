package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.UserMapper;
import bg.tasky.TaskManagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Long deleteUser(String username) {
        return userRepo.deleteUserByUsername(username);
    }

    public UserEntity updateUser(String username, UserDto newUser) {
        var oldUser = userRepo.getUserByUsername(username);

        if (newUser.firstName() != null && !newUser.firstName().isEmpty()) {
            oldUser.setFirstName(newUser.firstName());
        }
        if (newUser.lastName() != null && !newUser.lastName().isEmpty()) {
            oldUser.setLastName(newUser.lastName());
        }
        if (newUser.username() != null && !newUser.username().isEmpty()) {
            oldUser.setUsername(newUser.username());
        }

        return userRepo.save(oldUser);
    }
}
