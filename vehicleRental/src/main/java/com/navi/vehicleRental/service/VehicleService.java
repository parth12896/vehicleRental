package com.navi.vehicleRental.service;

import com.navi.vehicleRental.DTO.AddVehicleDTO;
import com.navi.vehicleRental.model.Branch;
import com.navi.vehicleRental.model.BranchInventory;
import com.navi.vehicleRental.model.Vehicle;
import com.navi.vehicleRental.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private BranchService branchService;
    @Autowired
    private BranchInventoryService branchInventoryService;
    @Autowired
    private BranchConfigService branchConfigService;

    public List<Vehicle> getAll(){
        return vehicleRepository.findAll();
    }

    public boolean onboard(AddVehicleDTO vehicleDTO){

        Vehicle vehicleResponse;
        try {
            validateOnboarding(vehicleDTO);

            Vehicle newVehicle = new Vehicle();
            newVehicle.setRegId(vehicleDTO.getRegNumber());
            newVehicle.setName(vehicleDTO.getVehicleName());
            newVehicle.setType(vehicleDTO.getType());
            vehicleResponse = vehicleRepository.create(newVehicle);

            //Assign the vehicle to the branch
            Branch branch = branchService.findByName(vehicleDTO.getBranchName());
            BranchInventory inventory = new BranchInventory();
            inventory.setVehicle(vehicleResponse);
            inventory.setBranch(branch);
            inventory.setHourlyPrice(vehicleDTO.getHourlyPrice());
            branchInventoryService.add(inventory);
            return true;

        } catch (Exception e){
            log.error("Vehicle onboard failed - {}", e.getMessage());
            return false;
        }
    }

    private void validateOnboarding(AddVehicleDTO vehicleDTO){
        Branch branch = branchService.findByName(vehicleDTO.getBranchName());
        if (branch == null){
            throw new EntityNotFoundException("Branch doesn't exist");
        }

        Vehicle vehicle = vehicleRepository.findByRegId(vehicleDTO.getRegNumber());
        if(vehicle != null){
            throw new EntityExistsException("Vehicle already onboarded");
        }

        int totalCapacity = branchConfigService.getCapacity(branch.getId(), vehicleDTO.getType());
        if (totalCapacity == -1){
            throw new RuntimeException("Car Type - "+ vehicleDTO.getType().toString()
                    + " not allowed for branch - " + vehicleDTO.getBranchName());
        }

        int currentCapacity = (int) branch.getInventoryList().stream()
                .filter(inv -> inv.getVehicle().getType() == vehicleDTO.getType()).count();

        if (currentCapacity > totalCapacity){
            throw new RuntimeException("Capacity Full");
        }

    }
}
