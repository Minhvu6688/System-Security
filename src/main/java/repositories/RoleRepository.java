package repositories;

import model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Tìm kiếm vai trò theo tên
    Optional<Role> findByRoleName(String roleName);
}
