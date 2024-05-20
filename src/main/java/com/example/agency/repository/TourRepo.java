package com.example.agency.repository;

import com.example.agency.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepo extends JpaRepository<Tour, Long> {

    @Query("SELECT DISTINCT t.tourName FROM Tour t")
    List<String> findDistinctTourTypes();
}
