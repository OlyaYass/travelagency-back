package com.example.agency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="booked_tour")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookedTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name="client_name")
    private String clientFullName;
    @Column(name="client_email")
    private String clientEmail;
    @Column(name="booking_count")
    private int bookingCount;
    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tour_id")
    private Tour tour;

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
