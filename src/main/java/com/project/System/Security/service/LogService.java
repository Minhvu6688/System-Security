package com.project.System.Security.service;

import com.project.System.Security.dto.LogDto;
import com.project.System.Security.model.Log;
import com.project.System.Security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.LogRepository;
import com.project.System.Security.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class    LogService implements ILogService {

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
//        log.setTimestamp(new Timestamp(System.currentTimeMillis())); // Thêm timestamp khi tạo log
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

    @Override
    public List<LogDto> getLogsByUserId(Integer userId) {
        return logRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private LogDto convertToDTO(Log log) {
        LogDto logDto = new LogDto();
        logDto.setLogId(log.getLogId());
        logDto.setAction(log.getAction());
        logDto.setUserId(log.getUser().getUserId());
        logDto.setTimestamp(log.getTimestamp());
        return logDto;
    }

    // Phương thức để ghi lại các thao tác của người dùng
    public void logAction(String action, Integer userId) {
        LogDto logDto = new LogDto();
        logDto.setAction(action);
        logDto.setUserId(userId);
        createLog(logDto);
    }
}






//    // Thêm phương thức tìm kiếm người dùng theo tên đăng nhập
//    public LogDto getUserByUsername(Integer userId) {
//        Optional<Log> log = userRepository.findByUserId(userId);
//        if (log.isPresent()) {
//            return convertToDTO(log.get());
//        } else {
//            throw new RuntimeException("User not found");
//        }
