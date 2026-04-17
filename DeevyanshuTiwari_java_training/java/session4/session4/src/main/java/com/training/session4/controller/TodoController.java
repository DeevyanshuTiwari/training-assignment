package com.training.session4.controller;

import com.training.session4.dto.TodoDTO;
import com.training.session4.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    // Constructor Injection
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // POST /todos → Create new todo
    // @Valid triggers DTO validation automatically
    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoDTO dto) {
        TodoDTO created = todoService.createTodo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /todos → Get all todos
    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    // GET /todos/1 → Get todo by id
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    // PUT /todos/1 → Update todo
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO dto) {
        return ResponseEntity.ok(todoService.updateTodo(id, dto));
    }

    // DELETE /todos/1 → Delete todo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.deleteTodo(id));
    }
}