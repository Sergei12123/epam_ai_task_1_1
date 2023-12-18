package com.example.epam_ai_task_1_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Method to create a new task
    public Task createTask(Task task) {
        // Additional business logic/validation can be added here
        return taskRepository.save(task);
    }

    // Method to get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Method to get a task by ID
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    // Method to update an existing task
    public Task updateTask(Long taskId, Task updatedTask) {
        // Additional business logic/validation can be added here
        return taskRepository.findById(taskId)
                .map(existingTask -> {
                    existingTask.setTitle(updatedTask.getTitle());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setDueDate(updatedTask.getDueDate());
                    existingTask.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(existingTask);
                })
                .orElse(null); // Handle if the task with the given ID is not found
    }

    // Method to delete a task by ID
    public boolean deleteTask(Long taskId) {
        // Additional business logic/validation can be added here
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false; // Handle if the task with the given ID is not found
    }
}
