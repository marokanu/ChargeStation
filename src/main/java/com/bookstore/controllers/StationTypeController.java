package com.bookstore.controllers;

import com.bookstore.models.StationType;
import com.bookstore.repositories.StationTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/api/stationTypes")
public class StationTypeController {

    private final StationTypeRepository stationTypeRepository;

    public StationTypeController(StationTypeRepository stationTypeRepository) {
        this.stationTypeRepository = stationTypeRepository;
    }

    @GetMapping
    public List<StationType> getAll() {
        return stationTypeRepository.findAll();
    }

    @PostMapping
    public StationType create(@RequestBody StationType stationType) {
        return stationTypeRepository.save(stationType);
    }
}
