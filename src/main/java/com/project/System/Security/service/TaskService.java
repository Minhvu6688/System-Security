package com.project.System.Security.service;

import com.project.System.Security.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.project.System.Security.model.Branch;
import com.project.System.Security.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.System.Security.repositories.BranchRepository;
import com.project.System.Security.repositories.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setCreatedAt(taskDto.getCreatedAt());
        if (taskDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(taskDto.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            task.setBranch(branch);
        }
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    @Override
    public TaskDto updateTask(Integer id, TaskDto taskDto) {
        Task task = taskRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDescription(taskDto.getDescription());
        task.setCreatedAt(taskDto.getCreatedAt());
        if (taskDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(taskDto.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            task.setBranch(branch);
        }
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public TaskDto getTaskById(Integer id) {
        Task task = taskRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Task not found"));
        return convertToDTO(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByBranchId(Long branchId) {
        return taskRepository.findByBranchId(branchId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private TaskDto convertToDTO(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskId(task.getTaskId());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setBranchId(task.getBranch().getId());
        return taskDto;
    }
}
