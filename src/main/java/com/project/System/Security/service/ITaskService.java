package com.project.System.Security.service;

import com.project.System.Security.dto.TaskDto;

import java.util.List;

public interface ITaskService {
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(Integer id, TaskDto taskDto);
    void deleteTask(Integer id);
    TaskDto getTaskById(Integer id);
    List<TaskDto> getAllTasks();
    List<TaskDto> getTasksByBranchId(Long branchId);
}
