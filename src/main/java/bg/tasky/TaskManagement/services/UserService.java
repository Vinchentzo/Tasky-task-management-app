package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.UserMapper;
import bg.tasky.TaskManagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return userMapper.convertEntityToDto(currentUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::convertEntityToDto)
                .toList();
    }

    public UserDto getUserById(Long id) {
        return userMapper.convertEntityToDto(userRepo.findUserById(id));
    }

    public UserDto createUser(UserDto user) {
        var userEnt = userMapper.convertDtoToEntity(user);

        userRepo.save(userEnt);

        return user;
    }

    @Transactional
    public Long deleteUser(String username) {
        return userRepo.deleteUserByUsername(username);
    }

    public UserDto updateUser(String username, UserDto newUser) {
        var oldUser = userRepo.findUserByUsername(username);

        if (newUser.firstName() != null && !newUser.firstName().isEmpty()) {
            oldUser.setFirstName(newUser.firstName());
        }
        if (newUser.lastName() != null && !newUser.lastName().isEmpty()) {
            oldUser.setLastName(newUser.lastName());
        }
        if (newUser.username() != null && !newUser.username().isEmpty()) {
            oldUser.setUsername(newUser.username());
        }
        if (newUser.password() != null && !newUser.password().isEmpty()) {
            oldUser.setPassword(newUser.password());
        }

        //kvo staa kato update-vame tablicite

        userRepo.save(oldUser);

        return userMapper.convertEntityToDto(oldUser);
    }
}
