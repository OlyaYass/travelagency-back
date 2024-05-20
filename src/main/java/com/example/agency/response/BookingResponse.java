package com.example.agency.response;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookingResponse {
    private Long bookingId;
    private String clientFullName;
    private String clientEmail;
    private int bookingCount;
    private String bookingConfirmationCode;

    private TourResponse tour;

    public BookingResponse(Long bookingId, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
