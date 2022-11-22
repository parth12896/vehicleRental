package com.navi.vehicleRental.DTO;

import com.navi.vehicleRental.model.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {

    private String branchName;
    private VehicleType type;
    private int startTime;
    private int endTime;

}
