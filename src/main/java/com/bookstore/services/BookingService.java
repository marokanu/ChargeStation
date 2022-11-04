package com.bookstore.services;

import com.bookstore.dtos.BookingDto;
import com.bookstore.models.Booking;
import com.bookstore.models.Station;
import com.bookstore.repositories.BookingRepository;
import com.bookstore.repositories.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StationRepository stationRepository;

    public BookingService(BookingRepository bookingRepository, StationRepository stationRepository) {
        this.bookingRepository = bookingRepository;
        this.stationRepository = stationRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking create(BookingDto bookingDto) {

        Booking booking = new Booking();
        booking.setEndDate(bookingDto.getDate().plusMinutes(bookingDto.getDuration()));
        booking.setName(bookingDto.getName());
        booking.setStartDate(bookingDto.getDate());
        booking.setLicenseNumber(bookingDto.getLicenseNumber());

        Station station = stationRepository.findById(bookingDto.getStationId())
                .orElseThrow(() -> new RuntimeException("Not found it!"));
        List<Booking> bookingList = bookingRepository.findByEndDateAfterAndStartDateBeforeAndStation(
               bookingDto.getDate(),
               bookingDto.getDate().plusMinutes(bookingDto.getDuration()), station);
        if (bookingList.size() > 0) {
            throw new RuntimeException("Error interval is overlapping with existing booking");
        }
        if (station != null) {
            booking.setStation(station);
        }

        return bookingRepository.save(booking);
    }

    public Booking findID(Long id) {
        return findById(id);
    }

    private Booking findById(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new RuntimeException("Could not find the entity with the id: " + id);
        }

        return booking;
    }

    public void delete(Long id) {
        Booking booking = findById(id);
        bookingRepository.delete(booking);
    }

    public Booking update(Long id, BookingDto bookingDTO) {
        Booking booking = findById(id);
        booking.setEndDate(bookingDTO.getDate().plusMinutes(bookingDTO.getDuration()));
        booking.setName(bookingDTO.getName());
        booking.setStartDate(bookingDTO.getDate());
        booking.setLicenseNumber(bookingDTO.getLicenseNumber());
        return bookingRepository.save(booking);
    }
}
