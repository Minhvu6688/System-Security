package service;

import dto.RoleDto;
import model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByName(String name);

    //phuong thuc tao role moi
    Role createRole(RoleDto roleDTO);

    //phuong thuc lay tat ca cac role
    List<Role> getAllRoles();
}
