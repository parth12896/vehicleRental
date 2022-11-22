package com.navi.vehicleRental.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String regId;
    private String name;

    @Enumerated(EnumType.STRING)
    private VehicleType type;
}
