package com.nucleusTeq.event_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucleusTeq.event_service.dto.BookingRequest;
import com.nucleusTeq.event_service.dto.BookingResponse;
import com.nucleusTeq.event_service.service.BookingService;

@CrossOrigin
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<BookingResponse> bookTickets(
            Authentication authentication,
            @RequestBody BookingRequest request) {
        String userEmail = authentication.getName();
        BookingResponse response = bookingService.bookTickets(userEmail, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> cancelBooking(
            Authentication authentication,
            @PathVariable Long bookingId) {
        String userEmail = authentication.getName();
        BookingResponse response = bookingService.cancelBooking(userEmail, bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<BookingResponse>> getBookingHistory(
            Authentication authentication) {
        String userEmail = authentication.getName();
        List<BookingResponse> response = bookingService.getBookingHistory(userEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByEventId(
            @PathVariable Long eventId) {
        List<BookingResponse> response = bookingService.getBookingsByEventId(eventId);
        return ResponseEntity.ok(response);
    }

}
