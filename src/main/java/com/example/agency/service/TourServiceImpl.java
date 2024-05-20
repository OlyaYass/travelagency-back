package com.example.agency.service;

import com.example.agency.exception.InternalServerException;
import com.example.agency.exception.ResourceNotFoundException;
import com.example.agency.model.Tour;
import com.example.agency.repository.TourRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepo tourRepo;

    @Override
    public Tour addNewTour(MultipartFile file, String tourName, BigDecimal tourPrice) throws SQLException, IOException {
        Tour tour = new Tour();
        tour.setTourName(tourName);
        tour.setTourPrice(tourPrice);
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            tour.setPhoto(photoBlob);
        }
        return tourRepo.save(tour);
    }

    @Override
    public List<String> getAllTourTypes() {

        return tourRepo.findDistinctTourTypes();
    }

    @Override
    public List<Tour> getAllTours() {

        return tourRepo.findAll();
    }

    @Override
    public byte[] getTourPhotoByTourId(Long tourId) throws SQLException {
        Optional<Tour> theTour = tourRepo.findById(tourId);
        if (theTour.isEmpty()) {
            throw new ResourceNotFoundException("Извините, тур не найден!");
        }
        Blob photoBlob = theTour.get().getPhoto();
        if (photoBlob != null) {
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteTour(Long tourId) {
        Optional<Tour> theTour = tourRepo.findById(tourId);
        if (theTour.isPresent()) {
            tourRepo.deleteById(tourId);
        }
    }

    @Override
    public Tour updateTour(Long tourId, String tourName, BigDecimal tourPrice, byte[] photoBytes) {
        Tour tour = tourRepo.findById(tourId)
                .orElseThrow(() -> new ResourceNotFoundException("Тур не найден"));
        if (tourName != null) {
            tour.setTourName(tourName);
        }
        if (tourPrice != null) tour.setTourPrice(tourPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                tour.setPhoto(new SerialBlob(photoBytes));
            }catch (SQLException ex) {
                throw new InternalServerException("Ошибка обновления тура");
            }
        }
        return tourRepo.save(tour);
    }

    @Override
    public Optional<Tour> getTourById(Long tourId) {
        return Optional.of(tourRepo.findById(tourId).get());
    }
}
