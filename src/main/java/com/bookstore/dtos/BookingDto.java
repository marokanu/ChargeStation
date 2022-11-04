package com.bookstore.dtos;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private LocalDateTime date;

    @NonNull
    private Long stationId;

    @NonNull
    private String licenseNumber;

    private int duration;

}