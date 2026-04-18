package com.training.session4.service;

import com.training.session4.client.NotificationServiceClient;
import com.training.session4.dto.TodoDTO;
import com.training.session4.entity.Todo;
import com.training.session4.entity.TodoStatus;
import com.training.session4.exception.InvalidStatusException;
import com.training.session4.exception.TodoNotFoundException;
import com.training.session4.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)   // enables Mockito in JUnit 5
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;          // fake repository

    @Mock
    private NotificationServiceClient notificationServiceClient; // fake client

    @InjectMocks
    private TodoService todoService;                // real service with mocks injected

    private Todo sampleTodo;
    private TodoDTO sampleDTO;

    // Runs before EACH test — sets up fresh sample data
    @BeforeEach
    void setUp() {
        sampleTodo = new Todo("Buy Groceries", "Milk and eggs", TodoStatus.PENDING, LocalDateTime.now());
        sampleTodo.setId(1L);

        sampleDTO = new TodoDTO();
        sampleDTO.setTitle("Buy Groceries");
        sampleDTO.setDescription("Milk and eggs");
        sampleDTO.setStatus(TodoStatus.PENDING);
    }

    // ─── CREATE TESTS ─────────────────────────────────────────────────

    @Test
    void createTodo_ShouldReturnCreatedTodo() {
        // ARRANGE: tell fake repo what to return when save() is called
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // ACT: call the real service method
        TodoDTO result = todoService.createTodo(sampleDTO);

        // ASSERT: check the result is what we expect
        assertNotNull(result);
        assertEquals("Buy Groceries", result.getTitle());
        assertEquals(TodoStatus.PENDING, result.getStatus());

        // VERIFY: check that notification was actually called once
        verify(notificationServiceClient, times(1)).sendNotification(anyString());
    }

    @Test
    void createTodo_ShouldDefaultStatusToPending_WhenStatusIsNull() {
        // Arrange
        sampleDTO.setStatus(null);   // no status provided
        when(todoRepository.save(any(Todo.class))).thenReturn(sampleTodo);

        // Act
        TodoDTO result = todoService.createTodo(sampleDTO);

        // Assert — should default to PENDING
        assertEquals(TodoStatus.PENDING, result.getStatus());
    }

    // ─── GET ALL TESTS ────────────────────────────────────────────────

    @Test
    void getAllTodos_ShouldReturnListOfTodos() {
        // Arrange
        Todo todo2 = new Todo("Read Book", "Java book", TodoStatus.COMPLETED, LocalDateTime.now());
        todo2.setId(2L);
        when(todoRepository.findAll()).thenReturn(Arrays.asList(sampleTodo, todo2));

        // Act
        List<TodoDTO> result = todoService.getAllTodos();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Buy Groceries", result.get(0).getTitle());
        assertEquals("Read Book", result.get(1).getTitle());
    }

    @Test
    void getAllTodos_ShouldReturnEmptyList_WhenNoTodos() {
        // Arrange
        when(todoRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<TodoDTO> result = todoService.getAllTodos();

        // Assert
        assertTrue(result.isEmpty());
    }

    // ─── GET BY ID TESTS ──────────────────────────────────────────────

    @Test
    void getTodoById_ShouldReturnTodo_WhenExists() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));

        // Act
        TodoDTO result = todoService.getTodoById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Buy Groceries", result.getTitle());
    }

    @Test
    void getTodoById_ShouldThrowException_WhenNotFound() {
        // Arrange — return empty Optional (not found)
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert — expect TodoNotFoundException to be thrown
        assertThrows(TodoNotFoundException.class, () -> todoService.getTodoById(99L));
    }

    // ─── UPDATE TESTS ─────────────────────────────────────────────────

    @Test
    void updateTodo_ShouldUpdateSuccessfully() {
        // Arrange
        TodoDTO updateDTO = new TodoDTO();
        updateDTO.setTitle("Updated Title");
        updateDTO.setDescription("Updated Desc");
        updateDTO.setStatus(TodoStatus.COMPLETED);  // PENDING → COMPLETED (valid)

        Todo updatedTodo = new Todo("Updated Title", "Updated Desc", TodoStatus.COMPLETED, LocalDateTime.now());
        updatedTodo.setId(1L);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        // Act
        TodoDTO result = todoService.updateTodo(1L, updateDTO);

        // Assert
        assertEquals("Updated Title", result.getTitle());
        assertEquals(TodoStatus.COMPLETED, result.getStatus());
    }

    @Test
    void updateTodo_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateTodo(99L, sampleDTO));
    }

    @Test
    void updateTodo_ShouldThrowException_ForInvalidStatusTransition() {
        // Arrange — try to go PENDING → PENDING (invalid, same status)
        sampleTodo.setStatus(TodoStatus.PENDING);
        TodoDTO invalidDTO = new TodoDTO();
        invalidDTO.setTitle("Some Title");
        invalidDTO.setStatus(TodoStatus.PENDING);  // same status — invalid transition

        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));

        // Act + Assert
        assertThrows(InvalidStatusException.class,
                () -> todoService.updateTodo(1L, invalidDTO));
    }

    // ─── DELETE TESTS ─────────────────────────────────────────────────

    @Test
    void deleteTodo_ShouldDeleteSuccessfully() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));
        doNothing().when(todoRepository).deleteById(1L);

        // Act
        String result = todoService.deleteTodo(1L);

        // Assert
        assertEquals("Todo with id 1 deleted successfully", result);

        // Verify deleteById was actually called once
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTodo_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(TodoNotFoundException.class,
                () -> todoService.deleteTodo(99L));

        // Verify deleteById was NEVER called
        verify(todoRepository, never()).deleteById(any());
    }
}