package repositories;

import model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Tìm kiếm vai trò theo tên
    Optional<Role> findByName(String name);
}