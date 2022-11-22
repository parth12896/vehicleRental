package com.navi.vehicleRental.repository;

import com.navi.vehicleRental.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    default Vehicle create(Vehicle vehicle){
        if(findByRegId(vehicle.getRegId()) == null){
            return save(vehicle);
        }
        else {
            throw new EntityExistsException("Vehicle Already Exists");
        }
    }

    Vehicle findByRegId(String regId);
}
