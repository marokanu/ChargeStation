package com.bookstore.dtos;

import lombok.Data;

@Data
public class StationDto {
    private Long id;

    private String name;

    private String location;

    private boolean open;

    private Long stationTypeId;
}