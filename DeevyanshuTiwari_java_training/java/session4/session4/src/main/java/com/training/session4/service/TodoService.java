package com.training.session4.service;

import com.training.session4.dto.TodoDTO;
import com.training.session4.entity.Todo;
import com.training.session4.entity.TodoStatus;
import com.training.session4.exception.InvalidStatusException;
import com.training.session4.exception.TodoNotFoundException;
import com.training.session4.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    // Constructor Injection
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // ─── Convert Entity → DTO (for sending response to user) ──────────
    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus());
        return dto;
    }

    // ─── Convert DTO → Entity (for saving to database) ────────────────
    private Todo convertToEntity(TodoDTO dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        // Default status to PENDING if not provided
        todo.setStatus(dto.getStatus() != null ? dto.getStatus() : TodoStatus.PENDING);
        // Set createdAt automatically — user cannot control this
        todo.setCreatedAt(LocalDateTime.now());
        return todo;
    }

    // ─── CREATE ───────────────────────────────────────────────────────
    public TodoDTO createTodo(TodoDTO dto) {
        Todo todo = convertToEntity(dto);
        Todo saved = todoRepository.save(todo);
        return convertToDTO(saved);
    }

    // ─── GET ALL ──────────────────────────────────────────────────────
    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────
    public TodoDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return convertToDTO(todo);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────
    public TodoDTO updateTodo(Long id, TodoDTO dto) {
        // Find existing todo or throw exception
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        // Validate status transition if status is being changed
        if (dto.getStatus() != null && !dto.getStatus().equals(existing.getStatus())) {
            validateStatusTransition(existing.getStatus(), dto.getStatus());
        }

        // Update fields
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        if (dto.getStatus() != null)
            existing.setStatus(dto.getStatus());

        Todo updated = todoRepository.save(existing);
        return convertToDTO(updated);
    }

    // ─── DELETE ───────────────────────────────────────────────────────
    public String deleteTodo(Long id) {
        // Check if exists first
        todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        todoRepository.deleteById(id);
        return "Todo with id " + id + " deleted successfully";
    }

    // ─── STATUS TRANSITION VALIDATOR ──────────────────────────────────
    private void validateStatusTransition(TodoStatus current, TodoStatus next) {
        // Only allowed: PENDING → COMPLETED  or  COMPLETED → PENDING
        boolean validTransition =
                (current == TodoStatus.PENDING && next == TodoStatus.COMPLETED) ||
                        (current == TodoStatus.COMPLETED && next == TodoStatus.PENDING);

        if (!validTransition)
            throw new InvalidStatusException(
                    "Invalid status transition from " + current + " to " + next
            );
    }
}