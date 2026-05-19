package com.nucleusTeq.event_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nucleusTeq.event_service.dto.EventRequest;
import com.nucleusTeq.event_service.dto.EventResponse;
import com.nucleusTeq.event_service.exception.BadRequestException;
import com.nucleusTeq.event_service.exception.GlobalExceptionHandler;
import com.nucleusTeq.event_service.service.EventService;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private ObjectMapper objectMapper;

    private EventRequest eventRequest;
    private EventResponse eventResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        eventRequest = new EventRequest();
        eventRequest.setTitle("Test Event");
        eventRequest.setDescription("Description");
        eventRequest.setEventDateTime(LocalDateTime.now().plusDays(5));
        eventRequest.setVenue("Test Venue");
        eventRequest.setTotalSeats(100);
        eventRequest.setPrice(50.0);

        eventResponse = new EventResponse(1L, "Test Event", "Description", "Test Venue",
                LocalDateTime.now().plusDays(5), 100, 100, false, 50.0);
    }

    @Test
    void testCreateEvent() throws Exception {
        when(eventService.createEvent(any(EventRequest.class))).thenReturn(eventResponse);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Event"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(eventResponse));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Event"));
    }

    @Test
    void testGetUpcomingEvents() throws Exception {
        when(eventService.getUpcomingEvents()).thenReturn(Arrays.asList(eventResponse));

        mockMvc.perform(get("/api/events/upcoming"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetEventById() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(eventResponse);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateEvent() throws Exception {
        when(eventService.updateEvent(eq(1L), any(EventRequest.class))).thenReturn(eventResponse);

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCancelEvent() throws Exception {
        eventResponse.setCancelled(true);
        when(eventService.cancelEvent(1L)).thenReturn(eventResponse);

        mockMvc.perform(put("/api/events/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cancelled").value(true));
    }

    @Test
    void testHandleIllegalArgumentException() throws Exception {
        when(eventService.createEvent(any(EventRequest.class)))
                .thenThrow(new BadRequestException("Invalid event"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid event"));
    }

    @Test
    void testHandleObjectOptimisticLockingFailureException() throws Exception {
        when(eventService.updateEvent(eq(1L), any(EventRequest.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Event", 1));

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("This resource was modified by someone else concurrently. Please refresh and try again."));
    }

    @Test
    void testHandleDataIntegrityViolationException() throws Exception {
        when(eventService.createEvent(any(EventRequest.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate"));

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid data submitted. Ensure values meet constraints."));
    }
}
