package com.project.System.Security.repositories;

import com.project.System.Security.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBranchId(Long branchId);
}
