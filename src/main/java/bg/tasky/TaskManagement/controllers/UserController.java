package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserEntity> fetchUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create/user")
    public UserEntity createUser(@RequestBody UserDto user){
        return userService.createUser(user);
    }

}
