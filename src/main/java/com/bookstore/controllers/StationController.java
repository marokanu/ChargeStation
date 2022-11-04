package com.bookstore.controllers;

import com.bookstore.dtos.StationDto;
import com.bookstore.models.Location;
import com.bookstore.models.Station;
import com.bookstore.models.StationType;
import com.bookstore.repositories.LocationRepository;
import com.bookstore.repositories.StationRepository;
import com.bookstore.repositories.StationTypeRepository;
import com.bookstore.services.StationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api/stations")
public class StationController {

    private final StationRepository stationRepository;
    private final StationTypeRepository stationTypeRepository;
    private final LocationRepository locationRepository;
    private final StationService stationService;

    public StationController(StationRepository stationRepository, StationTypeRepository stationTypeRepository, LocationRepository locationRepository, StationService stationService) {
        this.stationRepository = stationRepository;
        this.stationTypeRepository = stationTypeRepository;
        this.locationRepository = locationRepository;
        this.stationService = stationService;
    }

    @GetMapping
    public List<Station> getAll(){
        return stationRepository.findAll();
    }

    @GetMapping("/sort")
    public List<Station> getSortedStations(@RequestParam(defaultValue = "id") String sortBy,
                                           @RequestParam(defaultValue = "ASC") String sortOrder) {
        return stationService.getSortedStations(sortBy, sortOrder);
    }

    @PostMapping
    public Station create(@RequestBody StationDto stationDto) {
        StationType stationType = stationTypeRepository.findById(stationDto.getStationTypeId())
                .orElseThrow(() -> new RuntimeException("Error while retrieving stationDto type "));
        Location location = locationRepository .findById(stationDto.getId())
                .orElseThrow(() -> new RuntimeException("Error while retrieving stationDto type "));
        Station station = new Station();
        station.setStationType(stationType);
        station.setName(stationDto.getName());
        station.setLocation(location);
        station.setOpen(stationDto.isOpen());
        return stationRepository.save(station);
    }
}
