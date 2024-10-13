package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/fetch/users")
    public List<UserEntity> fetchUsers() {
        return userService.getAllUsers();
    }
}
