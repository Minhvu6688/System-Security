package service;

import dto.LogDto;
import lombok.RequiredArgsConstructor;
import model.Log;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.LogRepository;
import repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService implements ILogService {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public LogDto createLog(LogDto logDto) {
        Log log = new Log();
        log.setAction(logDto.getAction());
        if (logDto.getUserId() != null) {
            User user = userRepository.findById(Long.valueOf(logDto.getUserId())).orElseThrow(() -> new RuntimeException("User not found"));
            log.setUser(user);
        }
        log = logRepository.save(log);
        System.out.println("Log created: " + log.getLogId());
        return convertToDTO(log);
    }

    @Override
    public void deleteLog(Integer id) {
        logRepository.deleteById(id);
        System.out.println("Log deleted: " + id);
    }

    @Override
    public LogDto getLogById(Integer id) {
        Log log = logRepository.findById(id).orElseThrow(() -> new RuntimeException("Log not found"));
        return convertToDTO(log);
    }

    @Override
    public List<LogDto> getAllLogs() {
        return logRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private LogDto convertToDTO(Log log) {
        LogDto logDto = new LogDto();
        logDto.setLogId(log.getLogId());
        logDto.setAction(log.getAction());
        logDto.setUserId(log.getUser().getUserId());
        logDto.setTimestamp(log.getTimestamp());
        return logDto;
    }
}
