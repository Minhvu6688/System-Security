package controller;

import dto.UserDTo;
import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/api/users")
//@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Api để tạo người dùng mới
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTo userDTo) {
        try {
            User user = userService.createUser(userDTo);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    //Api để lấy thông tin người dùng theo tên đăng nhập
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable("username")String username) {
        return userService.getUserByName(username)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    //Api để lấy thông tin người dùng theo ID
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
