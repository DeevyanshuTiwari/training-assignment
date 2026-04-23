package com.nucleusTeq.event_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nucleusTeq.event_service.dto.EventRequest;
import com.nucleusTeq.event_service.dto.EventResponse;
import com.nucleusTeq.event_service.entity.Event;
import com.nucleusTeq.event_service.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponse createEvent(EventRequest request) {
        validateEventRequest(request);
        if (!request.getEventDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date/time must be in the future.");
        }

        Event event = new Event();
        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription().trim());
        event.setVenue(request.getVenue().trim());
        event.setEventDateTime(request.getEventDateTime());
        event.setTotalSeats(request.getTotalSeats());
        event.setAvailableSeats(request.getTotalSeats());
        event.setCancelled(false);

        Event savedEvent = eventRepository.save(event);
        return mapToEventResponse(savedEvent);
    }

    @Override
    public List<EventResponse> getUpcomingEvents() {
        List<Event> upcomingEvents = eventRepository
                .findByCancelledFalseAndEventDateTimeAfterOrderByEventDateTimeAsc(LocalDateTime.now());
        return upcomingEvents.stream().map(this::mapToEventResponse).collect(Collectors.toList());
    }

    @Override
    public EventResponse updateEvent(Long eventId, EventRequest request) {
        validateEventRequest(request);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));

        if (event.getCancelled()) {
            throw new IllegalArgumentException("Cannot update a cancelled event.");
        }
        if (!event.getEventDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot update event after it has started.");
        }
        if (!request.getEventDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Updated event date/time must be in the future.");
        }
        if (request.getTotalSeats() < (event.getTotalSeats() - event.getAvailableSeats())) {
            throw new IllegalArgumentException("Total seats cannot be less than already booked seats.");
        }

        int alreadyBookedSeats = event.getTotalSeats() - event.getAvailableSeats();

        event.setTitle(request.getTitle().trim());
        event.setDescription(request.getDescription().trim());
        event.setVenue(request.getVenue().trim());
        event.setEventDateTime(request.getEventDateTime());
        event.setTotalSeats(request.getTotalSeats());
        event.setAvailableSeats(request.getTotalSeats() - alreadyBookedSeats);

        Event updatedEvent = eventRepository.save(event);
        return mapToEventResponse(updatedEvent);
    }

    @Override
    public EventResponse cancelEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found."));

        if (!event.getEventDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel event after it has started.");
        }
        event.setCancelled(true);

        Event cancelledEvent = eventRepository.save(event);
        return mapToEventResponse(cancelledEvent);
    }

    private void validateEventRequest(EventRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required.");
        }
        if (isBlank(request.getTitle()) || request.getTitle().trim().length() < 3) {
            throw new IllegalArgumentException("Event title must be at least 3 characters.");
        }
        if (isBlank(request.getDescription()) || request.getDescription().trim().length() < 10) {
            throw new IllegalArgumentException("Event description must be at least 10 characters.");
        }
        if (isBlank(request.getVenue()) || request.getVenue().trim().length() < 3) {
            throw new IllegalArgumentException("Venue must be at least 3 characters.");
        }
        if (request.getEventDateTime() == null) {
            throw new IllegalArgumentException("Event date/time is required.");
        }
        if (request.getTotalSeats() == null || request.getTotalSeats() <= 0) {
            throw new IllegalArgumentException("Total seats must be greater than 0.");
        }
    }

    private EventResponse mapToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getVenue(),
                event.getEventDateTime(),
                event.getTotalSeats(),
                event.getAvailableSeats(),
                event.getCancelled());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
