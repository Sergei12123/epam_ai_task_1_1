package com.example.epam_ai_task_1_1;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTaskTest() {
        Task taskToCreate = new Task("Test Task", "Description", LocalDateTime.now(), false);
        Task createdTask = new Task(1L, "Test Task", "Description", LocalDateTime.now(), false);

        when(taskRepository.save(any(Task.class))).thenReturn(createdTask);

        Task result = taskService.createTask(taskToCreate);

        assertEquals(createdTask, result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getAllTasksTest() {
        Task task1 = new Task(1L, "Task 1", "Description 1", LocalDateTime.now(), false);
        Task task2 = new Task(2L, "Task 2", "Description 2", LocalDateTime.now(), true);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals(task1, result.get(0));
        assertEquals(task2, result.get(1));
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskByIdTest() {
        Task task = new Task(1L, "Test Task", "Description", LocalDateTime.now(), false);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void updateTaskTest() {
        Task existingTask = new Task(1L, "Existing Task", "Description", LocalDateTime.now(), false);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", LocalDateTime.now(), true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals(updatedTask, result);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTaskTest() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean result = taskService.deleteTask(1L);

        assertTrue(result);
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTaskNotFoundTest() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        boolean result = taskService.deleteTask(1L);

        assertFalse(result);
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, never()).deleteById(1L);
    }
}
