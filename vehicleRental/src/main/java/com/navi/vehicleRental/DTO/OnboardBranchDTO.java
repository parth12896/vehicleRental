package com.navi.vehicleRental.DTO;

import com.navi.vehicleRental.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OnboardBranchDTO {

    private String branchName;
    private List<VehicleType> allowedTypes;
}
