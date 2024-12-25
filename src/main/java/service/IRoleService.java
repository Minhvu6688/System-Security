package service;

import dto.RoleDTO;
import model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByName(String name);

    //phuong thuc tao role moi
    Role createRole(RoleDTO roleDTO);

    //phuong thuc lay tat ca cac role
    List<Role> getAllRoles();
}
