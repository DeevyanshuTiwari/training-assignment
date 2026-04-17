package com.training.session4.repository;

import com.training.session4.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository gives you all basic CRUD methods for free:
    // save(), findAll(), findById(), deleteById(), existsById()
    // No need to write anything here for basic operations
}