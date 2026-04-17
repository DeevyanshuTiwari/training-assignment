package com.training.session4.dto;

import com.training.session4.entity.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoDTO {

    // id and createdAt are NOT here — user doesn't control these

    private Long id;  // only used in responses, not in requests

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, message = "Title must be at least 3 characters")
    private String title;

    @Size(max = 255, message = "Description too long")
    private String description;

    private TodoStatus status;  // optional — defaults to PENDING if null

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TodoStatus getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
}