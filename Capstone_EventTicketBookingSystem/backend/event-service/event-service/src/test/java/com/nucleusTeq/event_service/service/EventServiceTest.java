package com.nucleusTeq.event_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nucleusTeq.event_service.dto.EventRequest;
import com.nucleusTeq.event_service.dto.EventResponse;
import com.nucleusTeq.event_service.entity.Event;
import com.nucleusTeq.event_service.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private EventRequest validEventRequest;
    private Event mockEvent;

    @BeforeEach
    void setUp() {
        // Arrange: Setup standard test inputs
        validEventRequest = new EventRequest();
        validEventRequest.setTitle("Tech Conference");
        validEventRequest.setDescription("A great conference");
        validEventRequest.setVenue("Convention Center");
        validEventRequest.setEventDateTime(LocalDateTime.now().plusDays(10)); // Future date
        validEventRequest.setTotalSeats(100);
        validEventRequest.setPrice(150.0);

        mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setTitle("Tech Conference");
        mockEvent.setEventDateTime(LocalDateTime.now().plusDays(10));
        mockEvent.setTotalSeats(100);
        mockEvent.setAvailableSeats(100);
        mockEvent.setPrice(150.0);
        mockEvent.setCancelled(false);
    }

    // 1. Valid event creation (Success)
    @Test
    void testCreateEvent_Success() {
        // Arrange
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // Act
        EventResponse response = eventService.createEvent(validEventRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Tech Conference", response.getTitle());
        assertEquals(100, response.getAvailableSeats());
        assertFalse(response.getCancelled());
        verify(eventRepository).save(any(Event.class));
    }

    // 2. Invalid event creation (Past Date) (Failure)
    @Test
    void testCreateEvent_PastDate_ThrowsException() {
        // Arrange
        validEventRequest.setEventDateTime(LocalDateTime.now().minusDays(1)); // Past date

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent(validEventRequest);
        });

        assertEquals("Event date/time must be in the future.", exception.getMessage());
    }

    // 3. Invalid event creation (Negative Seats/Price) (Failure)
    @Test
    void testCreateEvent_NegativeSeats_ThrowsException() {
        // Arrange
        validEventRequest.setTotalSeats(-5); // Invalid seats

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent(validEventRequest);
        });

        assertEquals("Total seats must be greater than 0.", exception.getMessage());
    }

    // 4. Cancel Event (Success)
    @Test
    void testCancelEvent_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // Act
        EventResponse response = eventService.cancelEvent(1L);

        // Assert
        assertTrue(response.getCancelled()); // Should be marked as cancelled
        verify(eventRepository).save(mockEvent); // Ensure update was saved
    }

    // 5. Cancel Event that is already cancelled (Success - Idempotent)
    @Test
    void testCancelEvent_AlreadyCancelled_Success() {
        // Arrange
        mockEvent.setCancelled(true);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // Act
        EventResponse response = eventService.cancelEvent(1L);

        // Assert
        assertTrue(response.getCancelled());
        verify(eventRepository).save(mockEvent);
    }

    // 6. Cancel Event that already happened (Failure)
    @Test
    void testCancelEvent_PastEvent_ThrowsException() {
        // Arrange
        mockEvent.setEventDateTime(LocalDateTime.now().minusDays(1));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.cancelEvent(1L);
        });

        assertEquals("Cannot cancel event after it has started.", exception.getMessage());
    }

    // 7. Get All Events (Success)
    @Test
    void testGetAllEvents_Success() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(mockEvent));
        List<EventResponse> responses = eventService.getAllEvents();
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    // 8. Get Upcoming Events (Success)
    @Test
    void testGetUpcomingEvents_Success() {
        when(eventRepository.findByCancelledFalseAndEventDateTimeAfterOrderByEventDateTimeAsc(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(mockEvent));
        List<EventResponse> responses = eventService.getUpcomingEvents();
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    // 9. Get Event By Id (Success)
    @Test
    void testGetEventById_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        EventResponse response = eventService.getEventById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    // 10. Update Event (Success)
    @Test
    void testUpdateEvent_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        validEventRequest.setTitle("Updated Title");
        EventResponse response = eventService.updateEvent(1L, validEventRequest);

        assertNotNull(response);
        verify(eventRepository).save(any(Event.class));
    }

    // 11. Validation Failures
    @Test
    void testCreateEvent_ValidationFailures_ThrowsException() {
        // Missing request
        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(null));

        // Short title
        validEventRequest.setTitle("Hi");
        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(validEventRequest));

        // Short description
        validEventRequest.setTitle("Valid Title");
        validEventRequest.setDescription("Too short");
        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(validEventRequest));

        // Missing Date
        validEventRequest.setDescription("This is a valid description");
        validEventRequest.setEventDateTime(null);
        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(validEventRequest));
    }
    // 12. Update Event Exceptions
    @Test
    void testUpdateEvent_ValidationFailures_ThrowsException() {
        // Event not found
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(1L, validEventRequest));

        // Cannot update cancelled event
        mockEvent.setCancelled(true);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(1L, validEventRequest));

        // Cannot update event after it has started
        mockEvent.setCancelled(false);
        mockEvent.setEventDateTime(LocalDateTime.now().minusHours(1));
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(1L, validEventRequest));

        // Updated event date must be in the future
        mockEvent.setEventDateTime(LocalDateTime.now().plusDays(10));
        validEventRequest.setEventDateTime(LocalDateTime.now().minusHours(1));
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(1L, validEventRequest));

        // Total seats less than booked
        validEventRequest.setEventDateTime(LocalDateTime.now().plusDays(10));
        mockEvent.setTotalSeats(100);
        mockEvent.setAvailableSeats(50); // 50 seats booked
        validEventRequest.setTotalSeats(40); // Cannot be less than 50
        assertThrows(IllegalArgumentException.class, () -> eventService.updateEvent(1L, validEventRequest));
    }
}

