package com.bookstore.services;

import com.bookstore.models.Station;
import com.bookstore.repositories.StationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {


    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    public List<Station> getSortedStations(String sortBy, String sortOrder) {
        return stationRepository.findAll(Sort.by(Sort.Direction.valueOf(sortOrder), sortBy));
    }
}