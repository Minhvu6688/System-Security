package controller;


import dto.PasswordChangeDTO;
import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/api/users/password")

public class PasswordController {
    private final UserService userService;

    public PasswordController(UserService userService) {
        this.userService = userService;
    }

    // API để thay đổi mật khẩu
    @PostMapping("/{userId}")
    public ResponseEntity<User> changePassword(@PathVariable("userId") Long userId,
                                               @RequestBody PasswordChangeDTO passwordChangeDTO) {
        try {
            User user = userService.changePassword(userId, passwordChangeDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
