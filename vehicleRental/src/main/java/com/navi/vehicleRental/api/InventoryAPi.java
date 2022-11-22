package com.navi.vehicleRental.api;

import com.navi.vehicleRental.DTO.DisplayVehicleDTO;
import com.navi.vehicleRental.model.BranchInventory;
import com.navi.vehicleRental.service.BranchInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryAPi {

    @Autowired
    private BranchInventoryService branchInventoryService;

    @GetMapping("/")
    List<BranchInventory> getAll(){
        return branchInventoryService.getAll();
    }

    @GetMapping("/display_vehicles")
    List<DisplayVehicleDTO> displayAvailableVehicles(@RequestParam("branchName") String branchName,
                                                     @RequestParam("startTime") int startTime,
                                                     @RequestParam("endTime") int endTime){
        return branchInventoryService.getAllAvailableVehicles(branchName,startTime,endTime);
    }
}
