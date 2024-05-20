package com.example.agency.service;

import com.example.agency.model.Tour;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TourService {
    Tour addNewTour(MultipartFile photo, String tourName, BigDecimal tourPrice) throws SQLException, IOException;

    List<String> getAllTourTypes();
    List<Tour> getAllTours();

    byte[] getTourPhotoByTourId(Long tourId) throws SQLException;

    void deleteTour(Long tourId);

    Tour updateTour(Long tourId, String tourName, BigDecimal tourPrice, byte[] photoBytes);

    Optional<Tour> getTourById(Long tourId);
}
