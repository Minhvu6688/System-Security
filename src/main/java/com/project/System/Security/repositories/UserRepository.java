package com.project.System.Security.repositories;

import com.project.System.Security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByBranchId(Long branchId);
//    List<User> findByRoleId(Integer roleName);

    @Query("SELECT u FROM User u WHERE u.role.roleId = :roleId")
    List<User> findByRoleId(@Param("roleId") Integer roleId);

    boolean existsByUsername(String username);
}
