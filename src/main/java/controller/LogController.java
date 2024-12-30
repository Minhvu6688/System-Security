package controller;

import dto.LogDto;
import lombok.RequiredArgsConstructor;
import model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ILogService;
import service.LogService;

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
}
