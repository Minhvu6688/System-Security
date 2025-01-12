/*
package com.project.System.Security.repositories;

import com.project.System.Security.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
//    List<Shift> findByAssignedEmployee(Integer assignedEmployee);

    @Query("SELECT s FROM Shift s WHERE s.assignedEmployee.userId = :userId")
    List<Shift> findByAssignedEmployeeId(@Param("userId") Integer userId);
}
*/
