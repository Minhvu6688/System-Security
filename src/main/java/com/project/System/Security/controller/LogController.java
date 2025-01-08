package com.project.System.Security.controller;

import com.project.System.Security.dto.LogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.System.Security.service.ILogService;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @Autowired
    private ILogService logService;

    @PostMapping
    public ResponseEntity<LogDto> createLog(@RequestBody LogDto logDTO) {
        LogDto createdLog = logService.createLog(logDTO);
        return ResponseEntity.ok(createdLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Integer id) {
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogDto> getLogById(@PathVariable Integer id) {
        LogDto logDTO = logService.getLogById(id);
        return ResponseEntity.ok(logDTO);
    }

    @GetMapping
    public ResponseEntity<List<LogDto>> getAllLogs() {
        List<LogDto> logs = logService.getAllLogs();
        return ResponseEntity.ok(logs);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LogDto>> getLogsByUserId(@PathVariable Integer userId) {
        List<LogDto> logs = logService.getLogsByUserId(userId);
        return ResponseEntity.ok(logs);
    }
}
