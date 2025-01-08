package com.project.System.Security.repositories;

import com.project.System.Security.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByBranchId(Long branchId);

//    List<Category> findByParentId(Long parentId);

    @Query("SELECT c FROM Category c WHERE c.parentCategory.id = :parentId")
    List<Category> findByParentCategoryId(@Param("parentId") Long parentId);
}
