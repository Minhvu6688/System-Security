package com.project.System.Security.controller;

import com.project.System.Security.components.JwtTokenUtil;
import com.project.System.Security.dto.UserDto;
import com.project.System.Security.dto.UserLoginDto;
import com.project.System.Security.model.LoginResponse;

import com.project.System.Security.model.User;
import com.project.System.Security.service.IUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") //http://localhost:8080/api/api/users
public class UserController {

    private final IUserService iUserService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    // Tạo người dùng mới
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            log.info("Received UserDto: {}", userDto);
            // Kiểm tra dữ liệu đầu vào
            if (userDto.getRoleId() == null) {
                return ResponseEntity.badRequest().body("Role ID is required");
            }
            if (userDto.getBranchId() == null) {
                return ResponseEntity.badRequest().body("Branch ID is required");
            }

            UserDto createdUser = iUserService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            log.error("Error while creating user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating user");
        }
    }

    // Cập nhật người dùng
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDto userDto) {
        UserDto updatedUser = iUserService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        iUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy thông tin người dùng theo id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto userDto = iUserService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    // Lấy danh sách tất cả người dùng
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = iUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Lấy danh sách người dùng theo roleId
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<UserDto>> getUsersByRoleId(@PathVariable Long roleId) {
        List<UserDto> users = iUserService.getUsersByRoleId(roleId);
        return ResponseEntity.ok(users);
    }

    // Lấy danh sách người dùng theo branchId
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserDto>> getUsersByBranchId(@PathVariable Long branchId) {
        List<UserDto> users = iUserService.getUsersByBranchId(branchId);
        return ResponseEntity.ok(users);
    }


    //login method
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid UserLoginDto userLoginDto) {
        // Thực hiện logic đăng nhập
        //Kiem tra thong tin dang nhap va sinh token
//        try {
//            User user = iUserService.validateUser(userLoginDto.getUsername(), userLoginDto.getPassword());
//            String token = JwtTokenUtil.generateToken(user);
//            //Tra ve token trong response
//            return ResponseEntity.ok(token);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
        try {
            String token = iUserService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

}
    // Exception handler for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}


//    @PostMapping("/register")
//    public ResponseEntity<User> register(@RequestBody User user) {
//        try {
//            User createdUser = IUserService.createUser(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }


