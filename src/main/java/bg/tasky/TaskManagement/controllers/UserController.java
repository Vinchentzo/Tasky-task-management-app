package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> fetchUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/user/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDto user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/user/delete")
    public Long deleteUser(@RequestParam String username){
        return userService.deleteUser(username);

    }

    @PatchMapping("user/update")
    public ResponseEntity<UserEntity> updateUser(@RequestParam String username, @RequestBody UserDto user){
        return ResponseEntity.ok(userService.updateUser(username, user));
    }

}
