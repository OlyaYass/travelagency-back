package com.example.agency.service;

import com.example.agency.exception.ResourceNotFoundException;
import com.example.agency.model.BookedTour;
import com.example.agency.model.Tour;
import com.example.agency.repository.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final TourService tourService;

    @Override
    public List<BookedTour> getAllBookings() {

        return bookingRepo.findAll();
    }

    @Override
    public List<BookedTour> getBookingsByUserEmail(String email) {
        return bookingRepo.findByClientEmail(email);
    }

    @Override
    public List<BookedTour> getAllBookingsByTourId(Long tourId) {

        return bookingRepo.findByTourId(tourId);
    }

    @Override
    public void cancelBooking(Long bookingId) {

        bookingRepo.deleteById(bookingId);
    }

    @Override
    public String saveBooking(Long tourId, BookedTour bookingRequest) {
        Tour tour = tourService.getTourById(tourId).get();
        tour.addBooking(bookingRequest);
        bookingRepo.save(bookingRequest);
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedTour findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepo.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Нет бронирований с номером :"+confirmationCode));

    }



}
