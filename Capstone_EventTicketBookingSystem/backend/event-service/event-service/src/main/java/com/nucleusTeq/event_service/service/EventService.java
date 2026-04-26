package com.nucleusTeq.event_service.service;

import java.util.List;

import com.nucleusTeq.event_service.dto.EventRequest;
import com.nucleusTeq.event_service.dto.EventResponse;

public interface EventService {

    EventResponse createEvent(EventRequest request);

    List<EventResponse> getUpcomingEvents();

    List<EventResponse> getAllEvents();

    EventResponse getEventById(Long eventId);

    EventResponse updateEvent(Long eventId, EventRequest request);

    EventResponse cancelEvent(Long eventId);
}
