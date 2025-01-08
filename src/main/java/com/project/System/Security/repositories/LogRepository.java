package com.project.System.Security.repositories;

import com.project.System.Security.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    // Tìm các log theo userId
    @Query("SELECT l FROM Log l WHERE l.user.userId = :userId")
    List<Log> findByUserId(@Param("userId") Integer userId);


}
