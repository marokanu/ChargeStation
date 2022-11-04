package com.bookstore.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "open")
    private boolean open;

    @ManyToOne()
    @JoinColumn(name = "station_type_id")
    private StationType stationType;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
