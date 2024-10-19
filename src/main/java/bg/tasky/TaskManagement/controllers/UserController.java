package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> fetchUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserEntity> authenticatedUser() { //change to userDto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserDto>> allUsers() {
        List <UserDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/user/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/user/delete")
    public Long deleteUser(@RequestParam String username){
        return userService.deleteUser(username);

    }

    @PatchMapping("user/update")
    public ResponseEntity<UserDto> updateUser(@RequestParam String username, @RequestBody UserDto user){
        return ResponseEntity.ok(userService.updateUser(username, user));
    }

}
