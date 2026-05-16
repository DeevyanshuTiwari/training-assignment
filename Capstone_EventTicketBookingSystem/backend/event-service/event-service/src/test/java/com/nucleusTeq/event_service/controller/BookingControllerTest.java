package com.nucleusTeq.event_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nucleusTeq.event_service.dto.BookingRequest;
import com.nucleusTeq.event_service.dto.BookingResponse;
import com.nucleusTeq.event_service.exception.BadRequestException;
import com.nucleusTeq.event_service.exception.GlobalExceptionHandler;
import com.nucleusTeq.event_service.service.BookingService;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private ObjectMapper objectMapper;

    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        bookingRequest = new BookingRequest();
        bookingRequest.setEventId(1L);
        bookingRequest.setSeatsRequested(2);

        bookingResponse = new BookingResponse(1L, 1L, "Test Event", LocalDateTime.now(), "user@test.com", "User Name", "1234567890", 2, "CONFIRMED", LocalDateTime.now());

        authentication = new UsernamePasswordAuthenticationToken("user@test.com", null, Collections.emptyList());
    }

    @Test
    void testBookTickets() throws Exception {
        when(bookingService.bookTickets(eq("user@test.com"), any(BookingRequest.class))).thenReturn(bookingResponse);

        mockMvc.perform(post("/api/bookings").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1))
                .andExpect(jsonPath("$.bookingStatus").value("CONFIRMED"));
    }

    @Test
    void testCancelBooking() throws Exception {
        bookingResponse.setBookingStatus("CANCELLED");
        when(bookingService.cancelBooking(eq("user@test.com"), eq(1L))).thenReturn(bookingResponse);

        mockMvc.perform(delete("/api/bookings/1").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingStatus").value("CANCELLED"));
    }

    @Test
    void testGetBookingHistory() throws Exception {
        when(bookingService.getBookingHistory("user@test.com")).thenReturn(Arrays.asList(bookingResponse));

        mockMvc.perform(get("/api/bookings/history").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1));
    }

    @Test
    void testGetBookingsByEventId() throws Exception {
        when(bookingService.getBookingsByEventId(1L)).thenReturn(Arrays.asList(bookingResponse));

        mockMvc.perform(get("/api/bookings/event/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value(1));
    }

    @Test
    void testHandleIllegalArgumentException() throws Exception {
        when(bookingService.bookTickets(anyString(), any(BookingRequest.class)))
                .thenThrow(new BadRequestException("Invalid booking"));

        mockMvc.perform(post("/api/bookings").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid booking"));
    }

    @Test
    void testHandleObjectOptimisticLockingFailureException() throws Exception {
        when(bookingService.bookTickets(anyString(), any(BookingRequest.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Booking", 1));

        mockMvc.perform(post("/api/bookings").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("This resource was modified by someone else concurrently. Please refresh and try again."));
    }

    @Test
    void testHandleDataIntegrityViolationException() throws Exception {
        when(bookingService.bookTickets(anyString(), any(BookingRequest.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        mockMvc.perform(post("/api/bookings").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid data submitted. Ensure values meet constraints."));
    }

    @Test
    void testHandleGenericException() throws Exception {
        when(bookingService.bookTickets(anyString(), any(BookingRequest.class)))
                .thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(post("/api/bookings").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Server Error: Something went wrong"));
    }
}
