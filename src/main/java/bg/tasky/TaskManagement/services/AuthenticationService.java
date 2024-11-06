package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.LoginUserDto;
import bg.tasky.TaskManagement.dtos.RegisterUserDto;
import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.UserMapper;
import bg.tasky.TaskManagement.repositories.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthenticationService {
    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    public AuthenticationService(
            UserRepo userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto signup(RegisterUserDto input) {
        UserEntity user = new UserEntity();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setBoards(new HashSet<>());

        userRepository.save(user);

        return userMapper.convertEntityToDto(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
