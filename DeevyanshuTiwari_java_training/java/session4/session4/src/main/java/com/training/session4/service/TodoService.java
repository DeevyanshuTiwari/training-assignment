package com.training.session4.service;

import com.training.session4.client.NotificationServiceClient;
import com.training.session4.dto.TodoDTO;
import com.training.session4.entity.Todo;
import com.training.session4.entity.TodoStatus;
import com.training.session4.exception.InvalidStatusException;
import com.training.session4.exception.TodoNotFoundException;
import com.training.session4.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;
    private final NotificationServiceClient notificationServiceClient;

    // Constructor Injection of BOTH dependencies
    public TodoService(TodoRepository todoRepository,
                       NotificationServiceClient notificationServiceClient) {
        this.todoRepository = todoRepository;
        this.notificationServiceClient = notificationServiceClient;
    }

    // ─── Convert Entity → DTO ─────────────────────────────────────────
    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus());
        return dto;
    }

    // ─── Convert DTO → Entity ─────────────────────────────────────────
    private Todo convertToEntity(TodoDTO dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setStatus(dto.getStatus() != null ? dto.getStatus() : TodoStatus.PENDING);
        todo.setCreatedAt(LocalDateTime.now());
        return todo;
    }

    // ─── CREATE ───────────────────────────────────────────────────────
    public TodoDTO createTodo(TodoDTO dto) {
        log.info("Creating new todo with title: {}", dto.getTitle());

        Todo todo = convertToEntity(dto);
        Todo saved = todoRepository.save(todo);

        log.info("Todo saved to database with id: {}", saved.getId());

        // Call notification service after creation
        notificationServiceClient.sendNotification(
                "New TODO created: '" + saved.getTitle() + "' with id: " + saved.getId()
        );

        return convertToDTO(saved);
    }

    // ─── GET ALL ──────────────────────────────────────────────────────
    public List<TodoDTO> getAllTodos() {
        log.info("Fetching all todos from database");
        List<TodoDTO> todos = todoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("Found {} todos", todos.size());
        return todos;
    }

    // ─── GET BY ID ────────────────────────────────────────────────────
    public TodoDTO getTodoById(Long id) {
        log.info("Fetching todo with id: {}", id);
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });
        return convertToDTO(todo);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────
    public TodoDTO updateTodo(Long id, TodoDTO dto) {
        log.info("Updating todo with id: {}", id);
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });

        if (dto.getStatus() != null && !dto.getStatus().equals(existing.getStatus())) {
            log.info("Status transition requested: {} → {}", existing.getStatus(), dto.getStatus());
            validateStatusTransition(existing.getStatus(), dto.getStatus());
        }

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        if (dto.getStatus() != null)
            existing.setStatus(dto.getStatus());

        Todo updated = todoRepository.save(existing);
        log.info("Todo updated successfully with id: {}", id);
        return convertToDTO(updated);
    }

    // ─── DELETE ───────────────────────────────────────────────────────
    public String deleteTodo(Long id) {
        log.info("Deleting todo with id: {}", id);
        todoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Todo not found with id: {}", id);
                    return new TodoNotFoundException(id);
                });
        todoRepository.deleteById(id);
        log.info("Todo deleted with id: {}", id);
        return "Todo with id " + id + " deleted successfully";
    }

    // ─── STATUS VALIDATOR ─────────────────────────────────────────────
    private void validateStatusTransition(TodoStatus current, TodoStatus next) {
        boolean valid =
                (current == TodoStatus.PENDING && next == TodoStatus.COMPLETED) ||
                        (current == TodoStatus.COMPLETED && next == TodoStatus.PENDING);

        if (!valid) {
            log.warn("Invalid status transition attempted: {} → {}", current, next);
            throw new InvalidStatusException(
                    "Invalid status transition from " + current + " to " + next
            );
        }
    }
}