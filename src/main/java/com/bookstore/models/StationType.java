package com.bookstore.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class StationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "power")
    private int power;

    @Column(name = "plugType")
    private String plugType;
}