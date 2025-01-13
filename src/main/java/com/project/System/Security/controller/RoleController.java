package com.project.System.Security.controller;


import com.project.System.Security.dto.RoleDto;
import com.project.System.Security.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")

public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // API để tạo role mới
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleDto roleDTO) {
        try {
            RoleDto role = roleService.createRole(roleDTO);
            return new ResponseEntity<>(role, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // API để lấy tất cả roles
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(),HttpStatus.OK);
    }

}


