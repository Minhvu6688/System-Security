package controller;

import dto.RoleDto;
import jakarta.validation.Valid;
import model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.RoleService;

@RestController
@RequestMapping("/api/roles")

public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // API để tạo role mới
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody @Valid RoleDto roleDTO) {
        try {
            Role role = roleService.createRole(roleDTO);
            return new ResponseEntity<>(role, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // API để lấy tất cả roles
    @GetMapping
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

}


