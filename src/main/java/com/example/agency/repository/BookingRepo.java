package com.example.agency.repository;

import com.example.agency.model.BookedTour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<BookedTour, Long> {
    List<BookedTour> findByTourId(Long tourId);

    Optional<BookedTour> findByBookingConfirmationCode(String confirmationCode);

    List<BookedTour> findByClientEmail(String email);

}
