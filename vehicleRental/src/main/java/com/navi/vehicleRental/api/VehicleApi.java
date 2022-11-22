package com.navi.vehicleRental.api;

import com.navi.vehicleRental.DTO.AddVehicleDTO;
import com.navi.vehicleRental.model.Vehicle;
import com.navi.vehicleRental.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/vehicle")
public class VehicleApi {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/")
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAll();
    }

    @PostMapping("/add")
    public boolean addNewVehicle(@RequestBody AddVehicleDTO vehicleDTO){
        return vehicleService.onboard(vehicleDTO);
    }
}
