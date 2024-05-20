package com.example.agency.controller;

import com.example.agency.exception.InvalidBookingRequestException;
import com.example.agency.exception.ResourceNotFoundException;
import com.example.agency.model.BookedTour;
import com.example.agency.model.Tour;
import com.example.agency.response.BookingResponse;
import com.example.agency.response.TourResponse;
import com.example.agency.service.BookingService;
import com.example.agency.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final TourService tourService;

    @GetMapping("/all-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedTour> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedTour booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }


    @PostMapping("/tour/{tourId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long tourId,
                                         @RequestBody BookedTour bookingRequest) {
        try {
            String confirmationCode=  bookingService.saveBooking(tourId, bookingRequest);
            return ResponseEntity.ok(
                    "Тур забронирован успешно, ваш номер бронирования : "+confirmationCode);
        }catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedTour booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }
    }

    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(@PathVariable String email) {
        List<BookedTour> bookings = bookingService.getBookingsByUserEmail(email);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedTour booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    private BookingResponse getBookingResponse(BookedTour booking) {
        Tour theTour = tourService.getTourById(booking.getTour().getId()).get();
        TourResponse tour = new TourResponse(theTour.getId(),
                theTour.getTourName(),
                theTour.getTourPrice());
        return new BookingResponse(booking.getBookingId(),
                booking.getClientFullName(),
                booking.getClientEmail(),
                booking.getBookingCount(),
                booking.getBookingConfirmationCode(), tour);
    }
}
