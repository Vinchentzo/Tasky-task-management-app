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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.getUserById(id));
//    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        UserDto user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<UserDto>> allUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

//    @PostMapping("/create")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){
//        return ResponseEntity.ok(userService.createUser(user));
//    }

    @DeleteMapping("/delete")
    public Long deleteUser(@RequestParam String username){
        return userService.deleteUser(username);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestParam String username, @RequestBody UserDto user){
        return ResponseEntity.ok(userService.updateUser(username, user));
    }

}
