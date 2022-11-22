package com.navi.vehicleRental.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BranchConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    @JsonIgnoreProperties(value = {"inventoryList", "branchStorageCapacity"})
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private int capacity = 10;
}
