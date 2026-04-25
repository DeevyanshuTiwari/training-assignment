package com.nucleusTeq.event_service.dto;

import java.time.LocalDateTime;

public class BookingResponse {

    private Long bookingId;
    private Long eventId;
    private String eventTitle;
    private String userEmail;
    private Integer seatsBooked;
    private String bookingStatus;
    private LocalDateTime bookingTime;

    public BookingResponse() {
    }

    public BookingResponse(Long bookingId, Long eventId, String eventTitle, String userEmail, Integer seatsBooked,
                           String bookingStatus, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.userEmail = userEmail;
        this.seatsBooked = seatsBooked;
        this.bookingStatus = bookingStatus;
        this.bookingTime = bookingTime;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(Integer seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
