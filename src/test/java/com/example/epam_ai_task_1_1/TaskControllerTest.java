package com.example.epam_ai_task_1_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTaskTest() {
        Task taskToCreate = new Task("Test Task", "Description", LocalDateTime.now(), false);
        Task createdTask = new Task(1L, "Test Task", "Description", LocalDateTime.now(), false);

        when(taskService.createTask(any(Task.class))).thenReturn(createdTask);

        ResponseEntity<Task> response = taskController.createTask(taskToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());
        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void getAllTasksTest() {
        Task task1 = new Task(1L, "Task 1", "Description 1", LocalDateTime.now(), false);
        Task task2 = new Task(2L, "Task 2", "Description 2", LocalDateTime.now(), true);

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(task1, response.getBody().get(0));
        assertEquals(task2, response.getBody().get(1));
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskByIdTest() {
        Task task = new Task(1L, "Test Task", "Description", LocalDateTime.now(), false);

        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskController.getTaskById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void updateTaskTest() {
        Task existingTask = new Task(1L, "Existing Task", "Description", LocalDateTime.now(), false);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", LocalDateTime.now(), true);

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateTask(1L, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());
        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void deleteTaskTest() {
        when(taskService.deleteTask(1L)).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void deleteTaskNotFoundTest() {
        when(taskService.deleteTask(1L)).thenReturn(false);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(1L);
    }
}
