package com.project.System.Security.repositories;

import com.project.System.Security.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByBranchId(Integer branchId);
}
