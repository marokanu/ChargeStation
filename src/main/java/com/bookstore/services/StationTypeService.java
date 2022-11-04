package com.bookstore.services;

import com.bookstore.models.StationType;
import com.bookstore.repositories.StationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationTypeService {


    public final StationTypeRepository stationTypeRepository;

    public StationTypeService(StationTypeRepository stationTypeRepository) {
        this.stationTypeRepository = stationTypeRepository;
    }

    public List<StationType> getStationType() {
        return stationTypeRepository.findAll();
    }
}
