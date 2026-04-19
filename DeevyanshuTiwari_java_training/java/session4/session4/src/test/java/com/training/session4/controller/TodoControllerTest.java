package com.training.session4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.session4.dto.TodoDTO;
import com.training.session4.entity.TodoStatus;
import com.training.session4.exception.GlobalExceptionHandler;
import com.training.session4.exception.TodoNotFoundException;
import com.training.session4.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest loads only the web layer — no database, no full app startup
@WebMvcTest(TodoController.class)
@Import({GlobalExceptionHandler.class})
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;         // simulates HTTP requests without a real server

    @MockBean
    private TodoService todoService; // fake service injected into controller

    @Autowired
    private ObjectMapper objectMapper; // converts objects to/from JSON

    private TodoDTO sampleDTO;

    @BeforeEach
    void setUp() {
        sampleDTO = new TodoDTO();
        sampleDTO.setId(1L);
        sampleDTO.setTitle("Buy Groceries");
        sampleDTO.setDescription("Milk and eggs");
        sampleDTO.setStatus(TodoStatus.PENDING);
    }

    // ─── POST /todos ──────────────────────────────────────────────────

    @Test
    void createTodo_ShouldReturn201_WhenValidInput() throws Exception {
        when(todoService.createTodo(any(TodoDTO.class))).thenReturn(sampleDTO);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDTO)))
                .andExpect(status().isCreated())           // 201
                .andExpect(jsonPath("$.title").value("Buy Groceries"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    // ─── GET /todos ───────────────────────────────────────────────────

    @Test
    void getAllTodos_ShouldReturn200_WithListOfTodos() throws Exception {
        TodoDTO dto2 = new TodoDTO();
        dto2.setId(2L);
        dto2.setTitle("Read Book");
        dto2.setStatus(TodoStatus.COMPLETED);

        List<TodoDTO> list = Arrays.asList(sampleDTO, dto2);
        when(todoService.getAllTodos()).thenReturn(list);

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())                // 200
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Buy Groceries"))
                .andExpect(jsonPath("$[1].title").value("Read Book"));
    }

    // ─── GET /todos/{id} ──────────────────────────────────────────────

    @Test
    void getTodoById_ShouldReturn200_WhenFound() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(sampleDTO);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Buy Groceries"));
    }

    @Test
    void getTodoById_ShouldReturn404_WhenNotFound() throws Exception {
        when(todoService.getTodoById(99L)).thenThrow(new TodoNotFoundException(99L));

        mockMvc.perform(get("/todos/99"))
                .andExpect(status().isNotFound());         // 404
    }

    // ─── PUT /todos/{id} ──────────────────────────────────────────────

    @Test
    void updateTodo_ShouldReturn200_WhenValid() throws Exception {
        TodoDTO updatedDTO = new TodoDTO();
        updatedDTO.setId(1L);
        updatedDTO.setTitle("Updated Title");
        updatedDTO.setStatus(TodoStatus.COMPLETED);

        when(todoService.updateTodo(eq(1L), any(TodoDTO.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    // ─── DELETE /todos/{id} ───────────────────────────────────────────

    @Test
    void deleteTodo_ShouldReturn200_WhenDeleted() throws Exception {
        when(todoService.deleteTodo(1L)).thenReturn("Todo with id 1 deleted successfully");

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Todo with id 1 deleted successfully"));
    }

    @Test
    void deleteTodo_ShouldReturn404_WhenNotFound() throws Exception {
        when(todoService.deleteTodo(99L)).thenThrow(new TodoNotFoundException(99L));

        mockMvc.perform(delete("/todos/99"))
                .andExpect(status().isNotFound());         // 404
    }
}