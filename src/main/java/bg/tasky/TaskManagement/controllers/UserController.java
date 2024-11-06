package bg.tasky.TaskManagement.controllers;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        UserDto user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete")
    public Long deleteUser(@RequestParam String username){
        return userService.deleteUser(username);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestParam String username, @RequestBody UserDto user){
        return ResponseEntity.ok(userService.updateUser(username, user));
    }
}
