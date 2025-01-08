package com.project.System.Security.service;

import com.project.System.Security.dto.LogDto;

import java.util.List;

public interface ILogService {
    LogDto createLog(LogDto logDto);
    void deleteLog(Integer id);
    LogDto getLogById(Integer id);
    List<LogDto> getAllLogs();

    List<LogDto> getLogsByUserId(Integer userId);
}
