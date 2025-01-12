/*
package com.project.System.Security.controller;

import com.project.System.Security.components.JwtTokenUtil;
import com.project.System.Security.dto.UserDto;
import com.project.System.Security.dto.UserLoginDto;
import com.project.System.Security.model.User;
import com.project.System.Security.service.IUserService;
import jakarta.validation.Valid;
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

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    // Tạo người dùng mới
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = iUserService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
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
    public ResponseEntity<List<UserDto>> getUsersByRoleId(@PathVariable Integer roleId) {
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
    public ResponseEntity<String> login(
            @RequestBody @Valid UserLoginDto userLoginDto) {
//        // Thực hiện logic đăng nhập
//        //Kiem tra thong tin dang nhap va sinh token
//        try {
//            User user = iUserService.validateUser(userLoginDto.getUsername(), userLoginDto.getPassword());
//            String token = JwtTokenUtil.generateToken(user);
//            //Tra ve token trong response
//            return ResponseEntity.ok(token);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//        //tra ve token trong response
    try {
        User user = iUserService.validateUser(userLoginDto.getUsername(), userLoginDto.getPassword());
        String token = JwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(token);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
    // Exception handler for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
    // Đăng ký người dùng
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) {
        System.out.println("User DTO received: " + userDto);
        if (userDto.getRoleId() == null) {
            throw new RuntimeException("Role ID is required");
        }
        if (userDto.getBranchId() == null) {
            throw new RuntimeException("Branch ID is required");
        }
        UserDto createdUser = iUserService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
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


*/
