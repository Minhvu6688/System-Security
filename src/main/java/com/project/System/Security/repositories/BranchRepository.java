package com.project.System.Security.repositories;

import com.project.System.Security.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
    @Query("SELECT b FROM Branch b WHERE b.parentBranch.id = :parentId")
    List<Branch> findByParentBranchId(@Param("parentId") Long parentId);

//    List<Branch> id(Long id);
}
