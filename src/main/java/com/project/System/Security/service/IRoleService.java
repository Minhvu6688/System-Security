package com.project.System.Security.service;

import com.project.System.Security.dto.RoleDto;

import java.util.List;

public interface IRoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto updateRole(Integer id, RoleDto roleDto);
    void deleteRole(Integer id);
    RoleDto getRoleById(Integer id);
    List<RoleDto> getAllRoles();

}
