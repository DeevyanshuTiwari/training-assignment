package com.nucleusTeq.event_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nucleusTeq.event_service.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Fetch only future and active events for "upcoming events" screen.
    List<Event> findByCancelledFalseAndEventDateTimeAfterOrderByEventDateTimeAsc(LocalDateTime currentDateTime);
}
