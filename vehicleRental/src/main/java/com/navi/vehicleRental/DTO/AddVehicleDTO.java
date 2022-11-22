package com.navi.vehicleRental.DTO;

import com.navi.vehicleRental.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddVehicleDTO {

    private String branchName;
    private VehicleType type;
    private String regNumber;
    private String vehicleName;
    private int hourlyPrice;
}
