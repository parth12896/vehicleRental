package com.navi.vehicleRental.DTO;

import com.navi.vehicleRental.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayVehicleDTO {

    private String name;
    private VehicleType type;
    private int hourlyPrice;

}
