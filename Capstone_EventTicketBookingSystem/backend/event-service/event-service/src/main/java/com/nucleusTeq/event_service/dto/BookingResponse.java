package com.nucleusTeq.event_service.dto;

import java.time.LocalDateTime;

public class BookingResponse {

    private Long bookingId;
    private Long eventId;
    private String eventTitle;
    private LocalDateTime eventDateTime;
    private String userEmail;
    private String userName;
    private String phone;
    private Integer seatsBooked;
    private String bookingStatus;
    private LocalDateTime bookingTime;

    public BookingResponse() {
    }

    public BookingResponse(Long bookingId, Long eventId, String eventTitle, LocalDateTime eventDateTime, String userEmail, String userName, String phone, Integer seatsBooked,
                           String bookingStatus, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDateTime = eventDateTime;
        this.userEmail = userEmail;
        this.userName = userName;
        this.phone = phone;
        this.seatsBooked = seatsBooked;
        this.bookingStatus = bookingStatus;
        this.bookingTime = bookingTime;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
