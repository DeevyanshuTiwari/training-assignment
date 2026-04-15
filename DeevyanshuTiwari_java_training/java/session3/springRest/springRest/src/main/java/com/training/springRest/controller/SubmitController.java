package com.training.springRest.controller;

import com.training.springRest.model.SubmitRequest;
import com.training.springRest.service.SubmitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubmitController {

    private final SubmitService submitService;

    // Constructor Injection
    public SubmitController(SubmitService submitService) {
        this.submitService = submitService;
    }

    // ─── TASK 2: POST /submit ──────────────────────────────────────────
    @PostMapping("/submit")
    public ResponseEntity<String> submitData(@RequestBody SubmitRequest request) {
        String message = submitService.processSubmission(request);
        // 201 = Created (success)
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}