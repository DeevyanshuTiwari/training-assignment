package com.nucleusTeq.event_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nucleusTeq.event_service.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Used to show booking history for a specific user.
    List<Booking> findByUserEmailOrderByBookingTimeDesc(String userEmail);

    // Used to show all bookings for a specific event (Organizer view).
    List<Booking> findByEventId(Long eventId);
}
