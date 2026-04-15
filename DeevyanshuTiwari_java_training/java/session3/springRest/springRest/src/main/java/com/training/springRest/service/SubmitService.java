package com.training.springRest.service;

import com.training.springRest.exception.InvalidInputException;
import com.training.springRest.model.SubmitRequest;
import org.springframework.stereotype.Service;

@Service
public class SubmitService {

    public String processSubmission(SubmitRequest request) {

        // Validate title
        if (request.getTitle() == null || request.getTitle().isEmpty())
            throw new InvalidInputException("title", "Title cannot be empty");

        // Validate description
        if (request.getDescription() == null || request.getDescription().isEmpty())
            throw new InvalidInputException("description", "Description cannot be empty");

        // Validate submittedBy
        if (request.getSubmittedBy() == null || request.getSubmittedBy().isEmpty())
            throw new InvalidInputException("submittedBy", "SubmittedBy cannot be empty");

        return "Data submitted successfully by " + request.getSubmittedBy();
    }
}