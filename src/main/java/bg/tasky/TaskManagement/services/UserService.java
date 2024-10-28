package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.UserMapper;
import bg.tasky.TaskManagement.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        return userRepo.findUserById(currentUser.getId());
    }

//    public UserEntity mapDtoToEntity(UserDto user) {
//        return userMapper.convertDtoToEntity(user);
//    }

    public UserEntity save(UserEntity user){
        return userRepo.save(user);
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

    public List<UserEntity> getAllUsersAsEntities() {
        return userRepo.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepo.findUserById(id);
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
            oldUser.setPassword(passwordEncoder.encode(newUser.password()));
        }

        userRepo.save(oldUser);

        return userMapper.convertEntityToDto(oldUser);
    }


}
