package com.nucleusTeq.event_service.dto;

import java.time.LocalDateTime;

public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private String venue;
    private LocalDateTime eventDateTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Boolean cancelled;

    public EventResponse() {
    }

    public EventResponse(Long id, String title, String description, String venue, LocalDateTime eventDateTime,
                         Integer totalSeats, Integer availableSeats, Boolean cancelled) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.eventDateTime = eventDateTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.cancelled = cancelled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }
}
