package com.project.System.Security.repositories;

import com.project.System.Security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Tìm kiếm vai trò theo tên
    Optional<Role> findByRoleName(String roleName);
}
