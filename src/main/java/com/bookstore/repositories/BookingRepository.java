package com.bookstore.repositories;

import com.bookstore.models.Booking;
import com.bookstore.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByEndDateAfterAndStartDateBeforeAndStation(LocalDateTime startDate, LocalDateTime endDate, Station station);

    @Query(value = "SELECT * FROM booking b " +
            "WHERE :startDate < b.end_date and :endDate > b.start_date", nativeQuery = true)
    List<Booking> findBookingOverlappingInterval(

            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value = "SELECT * FROM booking b " +
            "WHERE  b.end_date > :startDate  and b.start_date < :endDate", nativeQuery = true)
    List<Booking> findBookingOverlappingInterval2(

            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
