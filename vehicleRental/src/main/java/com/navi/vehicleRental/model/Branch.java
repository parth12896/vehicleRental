package com.navi.vehicleRental.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    @JsonIgnoreProperties("branch")
    private List<BranchInventory> inventoryList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    @JsonIgnoreProperties("branch")
    private List<BranchConfig> branchConfigCapacity;

}
