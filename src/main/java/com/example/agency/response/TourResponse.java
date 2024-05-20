package com.example.agency.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class TourResponse {
    private Long id;
    private String tourName;
    private BigDecimal tourPrice;
    private boolean isBooked;
    private String photo;
    private List<BookingResponse> bookings;

    public TourResponse(Long id, String tourName, BigDecimal tourPrice) {
        this.id = id;
        this.tourName = tourName;
        this.tourPrice = tourPrice;
    }

    public TourResponse(Long id, String tourName, BigDecimal tourPrice, boolean isBooked,
                        byte[] photoBytes, List<BookingResponse> bookings) {
        this.id = id;
        this.tourName = tourName;
        this.tourPrice = tourPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.bookings = bookings;
    }

}
