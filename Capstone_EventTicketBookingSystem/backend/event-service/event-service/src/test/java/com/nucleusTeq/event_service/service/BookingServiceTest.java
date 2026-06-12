package com.nucleusTeq.event_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.nucleusTeq.event_service.dto.BookingRequest;
import com.nucleusTeq.event_service.dto.BookingResponse;
import com.nucleusTeq.event_service.entity.Booking;
import com.nucleusTeq.event_service.entity.Event;
import com.nucleusTeq.event_service.repository.BookingRepository;
import com.nucleusTeq.event_service.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Event mockEvent;
    private BookingRequest validBookingRequest;
    private Booking mockBooking;

    @BeforeEach
    void setUp() {
        // Arrange: Setup common test data
        mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setTitle("Test Event");
        mockEvent.setEventDateTime(LocalDateTime.now().plusDays(5)); // Future event
        mockEvent.setTotalSeats(100);
        mockEvent.setAvailableSeats(50);
        mockEvent.setCancelled(false);

        validBookingRequest = new BookingRequest();
        validBookingRequest.setEventId(1L);
        validBookingRequest.setSeatsRequested(2);
        validBookingRequest.setUserName("John Doe");
        validBookingRequest.setPhone("1234567890");

        mockBooking = new Booking();
        mockBooking.setId(10L);
        mockBooking.setEvent(mockEvent);
        mockBooking.setUserEmail("john@example.com");
        mockBooking.setSeatsBooked(2);
        mockBooking.setBookingStatus("CONFIRMED");
    }

    // 1. Booking within available seats (Success)
    @Test
    void testBookTickets_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));
        when(bookingRepository.save(any(Booking.class))).thenReturn(mockBooking);

        // Act
        BookingResponse response = bookingService.bookTickets("john@example.com", validBookingRequest);

        // Assert
        assertNotNull(response);
        assertEquals(10L, response.getBookingId());
        assertEquals("CONFIRMED", response.getBookingStatus());
        assertEquals(48, mockEvent.getAvailableSeats()); // 50 - 2 = 48 seats should remain
        verify(eventRepository).save(mockEvent); // Ensure event was updated
        verify(bookingRepository).save(any(Booking.class)); // Ensure booking was saved
    }

    // 2. Booking exceeding capacity (Failure)
    @Test
    void testBookTickets_ExceedingCapacity_ThrowsException() {
        // Arrange
        validBookingRequest.setSeatsRequested(60); // Request more than available (50)
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookTickets("john@example.com", validBookingRequest);
        });

        assertEquals("Not enough seats available.", exception.getMessage());
    }

    // 3. Booking for past event (Failure)
    @Test
    void testBookTickets_PastEvent_ThrowsException() {
        // Arrange
        mockEvent.setEventDateTime(LocalDateTime.now().minusDays(1)); // Event already happened
        when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookTickets("john@example.com", validBookingRequest);
        });

        assertEquals("Cannot book tickets after event start time.", exception.getMessage());
    }

    // 4. Successful cancellation (seats restored)
    @Test
    void testCancelBooking_Success() {
        // Arrange
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(mockBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(mockBooking);

        // Act
        BookingResponse response = bookingService.cancelBooking("john@example.com", 10L);

        // Assert
        assertEquals("CANCELLED", response.getBookingStatus());
        assertEquals("CANCELLED", mockBooking.getBookingStatus());
        assertEquals(52, mockEvent.getAvailableSeats()); // 50 + 2 = 52 seats restored
        verify(eventRepository).save(mockEvent);
        verify(bookingRepository).save(mockBooking);
    }

    // 5. Cancellation after event time (Failure)
    @Test
    void testCancelBooking_AfterEventStarted_ThrowsException() {
        // Arrange
        mockEvent.setEventDateTime(LocalDateTime.now().minusHours(1)); // Event started 1 hr ago
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(mockBooking));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking("john@example.com", 10L);
        });

        assertEquals("Cannot cancel booking after event start time.", exception.getMessage());
    }

    // 6. Cancellation with less than 3 hours remaining (Failure)
    @Test
    void testCancelBooking_TooCloseToEvent_ThrowsException() {
        // Arrange
        mockEvent.setEventDateTime(LocalDateTime.now().plusHours(2)); // Event starts in 2 hrs (less than 3 hr limit)
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(mockBooking));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking("john@example.com", 10L);
        });

        assertEquals("Cancellations are only allowed up to 3 hours before the event starts.", exception.getMessage());
    }

    // 7. Get Booking History (Success)
    @Test
    void testGetBookingHistory_Success() {
        // Arrange
        when(bookingRepository.findByUserEmailOrderByBookingTimeDesc("john@example.com"))
                .thenReturn(Arrays.asList(mockBooking));

        // Act
        List<BookingResponse> responses = bookingService.getBookingHistory("john@example.com");

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(10L, responses.get(0).getBookingId());
    }

    // 8. Get Bookings by Event ID (Success)
    @Test
    void testGetBookingsByEventId_Success() {
        // Arrange
        when(bookingRepository.findByEventId(1L)).thenReturn(Arrays.asList(mockBooking));

        // Act
        List<BookingResponse> responses = bookingService.getBookingsByEventId(1L);

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    // 9. Validation failures
    @Test
    void testBookTickets_ValidationFailures_ThrowsException() {
        // Missing request
        assertThrows(RuntimeException.class, () -> bookingService.bookTickets("john@example.com", null));

        // Missing Event ID
        validBookingRequest.setEventId(null);
        assertThrows(RuntimeException.class, () -> bookingService.bookTickets("john@example.com", validBookingRequest));

        // Invalid Seats
        validBookingRequest.setEventId(1L);
        validBookingRequest.setSeatsRequested(0);
        assertThrows(RuntimeException.class, () -> bookingService.bookTickets("john@example.com", validBookingRequest));

        // Blank User Name
        validBookingRequest.setSeatsRequested(2);
        validBookingRequest.setUserName("   ");
        assertThrows(RuntimeException.class, () -> bookingService.bookTickets("john@example.com", validBookingRequest));
    }

    // 10. Cancel Booking validation failures
    @Test
    void testCancelBooking_ValidationFailures_ThrowsException() {
        // Not owner
        when(bookingRepository.findById(10L)).thenReturn(Optional.of(mockBooking));
        assertThrows(RuntimeException.class, () -> bookingService.cancelBooking("wronguser@example.com", 10L));

        // Already cancelled
        mockBooking.setBookingStatus("CANCELLED");
        assertThrows(RuntimeException.class, () -> bookingService.cancelBooking("john@example.com", 10L));
    }
}
