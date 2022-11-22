package com.navi.vehicleRental.service;

import com.navi.vehicleRental.DTO.BookingDTO;
import com.navi.vehicleRental.model.*;
import com.navi.vehicleRental.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BranchService branchService;

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }

    public int placeOrder(BookingDTO bookingDTO){
        try {
            validateBooking(bookingDTO);

            Branch branch = branchService.findByName(bookingDTO.getBranchName());
            List<Integer> availableVehicles = new ArrayList<>();

            //Fetch all available vehicle of type
            branch.getInventoryList().forEach( branchInventory -> {
                if (branchInventory.getVehicle().getType() == bookingDTO.getType()){
                    availableVehicles.add(branchInventory.getVehicle().getId());
                }
            });

            //filter out booked vehicles
            removeBookedVehicles(bookingDTO, branch, availableVehicles);

            int finalAmount = calculatePriceAndPlaceOrder(branch, availableVehicles, bookingDTO);
            return finalAmount;

        }catch (Exception e){

            log.error("Booking failed - "+ e.getMessage());
            return -1;
        }

    }

    private int calculatePriceAndPlaceOrder(Branch branch, List<Integer> availableVehicles, BookingDTO bookingDTO) {

        List<BranchInventory> inventoryList = branch.getInventoryList().stream()
                .filter(branchInventory -> {
                        if (availableVehicles.contains(branchInventory.getVehicle().getId()))
                            return true;
                        return false;
        }).sorted(Comparator.comparingInt(BranchInventory::getHourlyPrice)).collect(Collectors.toList());

        int finalAmount = inventoryList.get(0).getHourlyPrice() *
                (bookingDTO.getEndTime() - bookingDTO.getStartTime());

        //Surge Pricing
        if (((branch.getInventoryList().size() - inventoryList.size()) / branch.getInventoryList().size()) * 100.00 > 80.00)
            finalAmount += finalAmount * 0.1;


        updateDatabase(bookingDTO, branch, inventoryList, finalAmount);
        return finalAmount;
    }

    private void removeBookedVehicles(BookingDTO bookingDTO, Branch branch, List<Integer> availableVehicles) {
        List<Booking> bookedVehicles = getAllBookedVehicles(branch.getId(), bookingDTO.getType());
        if (bookedVehicles.size() > 0 || bookedVehicles != null) {
            getAllBookedVehicles(branch.getId(), bookingDTO.getType())
                    .forEach(booking -> {
                        if (booking.getVehicle().getType() == bookingDTO.getType()) {
                            if (booking.getStartTime() < bookingDTO.getEndTime()
                                    && booking.getEndTime() > bookingDTO.getStartTime()) {
                                availableVehicles.remove((Object) booking.getVehicle().getId());
                            }
                        }
                    });
        }
    }

    private void updateDatabase(BookingDTO bookingDTO, Branch branch, List<BranchInventory> inventoryList, int finalAmount) {
        Booking bookingData = new Booking();
        bookingData.setBranch(branch);
        bookingData.setAmountPaid(finalAmount);
        bookingData.setStartTime(bookingDTO.getStartTime());
        bookingData.setEndTime(bookingDTO.getEndTime());
        bookingData.setVehicleType(bookingDTO.getType());
        bookingData.setVehicle(inventoryList.get(0).getVehicle());
        bookingRepository.save(bookingData);
    }

    public List<Booking> getAllBookedVehicles(int branchId, VehicleType type){
        return bookingRepository.findAllByBranchIdAndVehicleType(branchId,type);
    }

    public List<Booking> getAllBookedVehicles(int branchId){
        return bookingRepository.findAllByBranchId(branchId);
    }

    private void validateBooking(BookingDTO bookingDTO){
        int startTime = bookingDTO.getStartTime();
        int endTime = bookingDTO.getEndTime();

        if (startTime > endTime || startTime < 0 || startTime > 24 || endTime < 0 || endTime > 24){
            throw  new RuntimeException("Invalid Time");
        }

        //Check if branch exists
        Branch branch = branchService.findByName(bookingDTO.getBranchName());
        if(branch == null)
            throw new EntityNotFoundException("Branch Not Found");

        //Check if branch has the type of vehicle
        if (branch.getInventoryList().stream().filter(
                branchInventory -> branchInventory.getVehicle().getType() == bookingDTO.getType())
                .findAny().isEmpty())

            throw new EntityNotFoundException(
                    "No Vehicle of Type " + bookingDTO.getType().toString() +
                            " found for branch "+ bookingDTO.getBranchName());

        //Check if vehicle is available for booking
        List<Booking> bookings = bookingRepository.findAllByBranchIdAndVehicleType(branch.getId(),bookingDTO.getType());

        int bookingCount = (int) bookings.stream().filter(booking -> {
            if (booking.getStartTime() < bookingDTO.getEndTime() && booking.getEndTime() > bookingDTO.getStartTime())
                return true;
            return false;
        }).count();

        int inventoryCount = (int) branch.getInventoryList().stream().filter(branchInventory ->
                        branchInventory.getVehicle().getType() == bookingDTO.getType()
                ).count();

        //Check if there are unbooked vehicles
        if(inventoryCount - bookingCount == 0)
            throw new RuntimeException("All vehicles of type " + bookingDTO.getType() + " are booked");
    }
}
