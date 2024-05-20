package com.example.agency.service;

import com.example.agency.model.BookedTour;

import java.util.List;

public interface BookingService {
    void cancelBooking(Long bookingId);
    List<BookedTour> getAllBookingsByTourId(Long tourId);

    String saveBooking(Long tourId, BookedTour bookingRequest);

    BookedTour findByBookingConfirmationCode(String confirmationCode);

    List<BookedTour> getAllBookings();

    List<BookedTour> getBookingsByUserEmail(String email);
}
