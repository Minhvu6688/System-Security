package controller;

import lombok.RequiredArgsConstructor;
import model.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.LogService;

import java.util.List;

@RestController
@RequestMapping("/api/logs")

public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }


    // API để lấy tất cả logs
    @GetMapping
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = logService.getAllLogs();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
