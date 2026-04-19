package com.training.session4.controller;

import com.training.session4.dto.TodoDTO;
import com.training.session4.service.TodoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    // Create logger — always pass the current class name
    private static final Logger log = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO dto) {
        log.info("Request received to create todo with title: {}", dto.getTitle());
        TodoDTO created = todoService.createTodo(dto);
        log.info("Todo created successfully with id: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        log.info("Request received to fetch all todos");
        List<TodoDTO> todos = todoService.getAllTodos();
        log.info("Returning {} todos", todos.size());
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        log.info("Request received to fetch todo with id: {}", id);
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO dto) {
        log.info("Request received to update todo with id: {}", id);
        TodoDTO updated = todoService.updateTodo(id, dto);
        log.info("Todo updated successfully with id: {}", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        log.info("Request received to delete todo with id: {}", id);
        String message = todoService.deleteTodo(id);
        log.info("Todo deleted with id: {}", id);
        return ResponseEntity.ok(message);
    }
}