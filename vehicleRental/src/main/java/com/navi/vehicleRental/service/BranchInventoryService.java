package com.navi.vehicleRental.service;

import com.navi.vehicleRental.DTO.DisplayVehicleDTO;
import com.navi.vehicleRental.model.Booking;
import com.navi.vehicleRental.model.Branch;
import com.navi.vehicleRental.model.BranchInventory;
import com.navi.vehicleRental.repository.BranchInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class BranchInventoryService {

    @Autowired
    private BranchInventoryRepository branchInventoryRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BranchService branchService;

    public List<BranchInventory> getAll(){
        return branchInventoryRepository.findAll();
    }

    public BranchInventory add(BranchInventory branchInventory){
        return branchInventoryRepository.save(branchInventory);
    }

    public List<DisplayVehicleDTO> getAllAvailableVehicles(String branchName, int startTime, int endTime){
        List<DisplayVehicleDTO> response = new ArrayList<>();

        try {
            validatePayload(branchName, startTime, endTime);
            Branch branch = branchService.findByName(branchName);

            List<Integer> availableVehicles = new ArrayList<>();
            branch.getInventoryList().forEach(branchInventory -> {
                availableVehicles.add(branchInventory.getVehicle().getId());
            });

            //filter out booked vehicles
            List<Booking> bookedVehicles = bookingService.getAllBookedVehicles(branch.getId());

            if (bookedVehicles != null || bookedVehicles.size() > 0) {
                bookedVehicles
                        .forEach(booking -> {
                            if (booking.getStartTime() < endTime
                                    && booking.getEndTime() > startTime) {
                                availableVehicles.remove((Object) booking.getVehicle().getId());
                            }
                        });
            }

            //Prepare Response
            prepareDTO(response, branch, availableVehicles);

            //Sort based on hourly price
            Collections.sort(response, Comparator.comparingInt(DisplayVehicleDTO::getHourlyPrice));

            return response;
        }catch (Exception e){
            log.error("Error while fetching available cars for branch {} : {}",branchName, e.getMessage());
            return response;
        }
    }

    private void prepareDTO(List<DisplayVehicleDTO> response, Branch branch, List<Integer> availableVehicles) {
        branch.getInventoryList()
                .forEach(branchInventory -> {
                    if (availableVehicles.contains(branchInventory.getVehicle().getId())){
                        response.add(new DisplayVehicleDTO(
                                branchInventory.getVehicle().getName(),
                                branchInventory.getVehicle().getType(),
                                branchInventory.getHourlyPrice()
                        ));
                    }
        });
    }

    private void validatePayload(String branchName, int startTime, int endTime){
        if (startTime > endTime || startTime < 0 || startTime > 24 || endTime < 0 || endTime > 24){
            throw  new RuntimeException("Invalid Time");
        }

        Branch branch = branchService.findByName(branchName);
        if (branch == null)
            throw new RuntimeException("No Branch name " + branchName + " found");
    }

}
