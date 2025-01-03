package service;

import dto.RoleDto;
import model.Role;
import org.springframework.stereotype.Service;
import repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service

public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public Role createRole(RoleDto roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


}
