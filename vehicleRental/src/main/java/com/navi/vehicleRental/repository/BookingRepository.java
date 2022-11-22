package com.navi.vehicleRental.repository;

import com.navi.vehicleRental.model.Booking;
import com.navi.vehicleRental.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    List<Booking> findAllByBranchIdAndVehicleType(int branchId, VehicleType type);

    List<Booking> findAllByBranchId(int branchId);

}
