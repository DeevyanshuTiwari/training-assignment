package com.nucleusTeq.event_service.service;

import java.util.List;

import com.nucleusTeq.event_service.dto.BookingRequest;
import com.nucleusTeq.event_service.dto.BookingResponse;

public interface BookingService {

    BookingResponse bookTickets(String userEmail, BookingRequest request);

    BookingResponse cancelBooking(String userEmail, Long bookingId);

    List<BookingResponse> getBookingHistory(String userEmail);
}
