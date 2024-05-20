package com.example.agency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Tour")
@AllArgsConstructor
@Getter
@Setter
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tour_name")
    private String tourName;
    @Column(name="tour_price")
    private BigDecimal tourPrice;
    @Column(name="is_booked")
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedTour> bookings;

    public Tour() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedTour bookedTour) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(bookedTour);
        bookedTour.setTour(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        bookedTour.setBookingConfirmationCode(bookingCode);
    }
}
