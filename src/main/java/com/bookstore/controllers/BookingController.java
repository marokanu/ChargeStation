package com.bookstore.controllers;

import com.bookstore.dtos.BookingDto;
import com.bookstore.models.Booking;
import com.bookstore.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> findAll() {
        return bookingService.findAll();
    }

    @GetMapping(value = "{id}", produces = "application/json")
    public Booking findOne(@PathVariable Long id) {
        return bookingService.findID(id);
    }

    @PutMapping("{id}")
    public Booking update(@PathVariable Long id, BookingDto bookingDTO) {
        return bookingService.update(id, bookingDTO);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Booking create(@Valid @RequestBody BookingDto bookingDTO) {
        return bookingService.create(bookingDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Long id) {

        bookingService.delete(id);
    }
}
