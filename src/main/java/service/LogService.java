package service;

import lombok.RequiredArgsConstructor;
import model.Log;
import model.User;
import org.springframework.stereotype.Service;
import repositories.LogRepository;

import java.util.List;

@Service

public class LogService implements ILogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void logAction(User user, String action) {
        Log log = new Log();
        log.setUserId(user.getId());
        log.setAction(action);
        logRepository.save(log);
    }

    @Override
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
