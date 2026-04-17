package com.training.session4.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity                  // tells JPA: this class is a database table
@Table(name = "todos")   // table name in database
public class Todo {

    @Id                                 // this is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)        // store as "PENDING"/"COMPLETED" not 0/1
    @Column(nullable = false)
    private TodoStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Default constructor (JPA needs this)
    public Todo() {
    }

    // Full constructor
    public Todo(String title, String description, TodoStatus status, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}