package com.project.System.Security.service;

import com.project.System.Security.dto.RoleDto;
import com.project.System.Security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        role = roleRepository.save(role);
        return convertToDTO(role);
    }

    @Override
    public RoleDto updateRole(Integer id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        role.setRoleName(roleDto.getRoleName());
        role = roleRepository.save(role);
        return convertToDTO(role);
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto getRoleById(Integer id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return convertToDTO(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Thêm phương thức tìm kiếm vai trò theo tên
    public RoleDto getRoleByName(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            return convertToDTO(role.get());
        } else {
            throw new RuntimeException("Role not found");
        }
    }

    private RoleDto convertToDTO(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }
}
