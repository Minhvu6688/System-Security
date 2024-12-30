package service;

import dto.LogDto;
import model.Log;
import model.User;

import java.util.List;

public interface ILogService {
    LogDto createLog(LogDto logDto);
    void deleteLog(Integer id);
    LogDto getLogById(Integer id);
    List<LogDto> getAllLogs();
}
