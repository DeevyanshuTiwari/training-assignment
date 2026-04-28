package com.nucleusTeq.event_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nucleusTeq.event_service.dto.BookingRequest;
import com.nucleusTeq.event_service.dto.BookingResponse;
import com.nucleusTeq.event_service.entity.Booking;
import com.nucleusTeq.event_service.entity.Event;
import com.nucleusTeq.event_service.repository.BookingRepository;
import com.nucleusTeq.event_service.repository.EventRepository;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public BookingResponse bookTickets(String userEmail, BookingRequest request) {
        if (isBlank(userEmail)) {
            throw new IllegalArgumentException("User email is required.");
        }
        validateBookingRequest(request);

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));

        if (event.getCancelled()) {
            throw new IllegalArgumentException("Cannot book tickets for a cancelled event.");
        }
        if (!event.getEventDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book tickets after event start time.");
        }
        if (event.getAvailableSeats() < request.getSeatsRequested()) {
            throw new IllegalArgumentException("Not enough seats available.");
        }

        if (!simulatePayment(userEmail, request.getSeatsRequested())) {
            throw new IllegalArgumentException("Payment failed. Booking not completed.");
        }

        // Prevent overbooking by reducing available seats in the same transaction.
        event.setAvailableSeats(event.getAvailableSeats() - request.getSeatsRequested());
        eventRepository.save(event);

        Booking booking = new Booking();
        booking.setUserEmail(userEmail.trim().toLowerCase());
        booking.setUserName(request.getUserName());
        booking.setPhone(request.getPhone());
        booking.setSeatsBooked(request.getSeatsRequested());
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus("CONFIRMED");
        booking.setEvent(event);

        Booking savedBooking = bookingRepository.save(booking);
        return mapToBookingResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(String userEmail, Long bookingId) {
        if (isBlank(userEmail)) {
            throw new IllegalArgumentException("User email is required.");
        }
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));

        if (!booking.getUserEmail().equalsIgnoreCase(userEmail.trim())) {
            throw new IllegalArgumentException("You can cancel only your own booking.");
        }
        if ("CANCELLED".equalsIgnoreCase(booking.getBookingStatus())) {
            throw new IllegalArgumentException("Booking is already cancelled.");
        }
        if (!booking.getEvent().getEventDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel booking after event start time.");
        }
        LocalDateTime cancelDeadline = booking.getEvent().getEventDateTime().minusHours(3);
        if (LocalDateTime.now().isAfter(cancelDeadline)) {
            throw new IllegalArgumentException("Cancellations are only allowed up to 3 hours before the event starts.");
        }

        booking.setBookingStatus("CANCELLED");
        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + booking.getSeatsBooked());

        eventRepository.save(event);
        Booking updatedBooking = bookingRepository.save(booking);
        return mapToBookingResponse(updatedBooking);
    }

    @Override
    public List<BookingResponse> getBookingHistory(String userEmail) {
        if (isBlank(userEmail)) {
            throw new IllegalArgumentException("User email is required.");
        }
        List<Booking> bookings = bookingRepository.findByUserEmailOrderByBookingTimeDesc(userEmail.trim().toLowerCase());
        return bookings.stream().map(this::mapToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByEventId(Long eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID is required.");
        }
        List<Booking> bookings = bookingRepository.findByEventId(eventId);
        return bookings.stream().map(this::mapToBookingResponse).collect(Collectors.toList());
    }

    private void validateBookingRequest(BookingRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required.");
        }
        if (request.getEventId() == null) {
            throw new IllegalArgumentException("Event ID is required.");
        }
        if (request.getSeatsRequested() == null || request.getSeatsRequested() <= 0) {
            throw new IllegalArgumentException("Seats requested must be greater than 0.");
        }
        if (isBlank(request.getUserName())) {
            throw new IllegalArgumentException("User name is required.");
        }
    }

    private boolean simulatePayment(String userEmail, Integer seatsRequested) {
        // Mock payment simulation. For now, always return success.
        return !isBlank(userEmail) && seatsRequested > 0;
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getEvent().getId(),
                booking.getEvent().getTitle(),
                booking.getEvent().getEventDateTime(),
                booking.getUserEmail(),
                booking.getUserName(),
                booking.getPhone(),
                booking.getSeatsBooked(),
                booking.getBookingStatus(),
                booking.getBookingTime());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
