package com.navi.vehicleRental.repository;

import com.navi.vehicleRental.model.BranchConfig;
import com.navi.vehicleRental.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchConfigRepository extends JpaRepository<BranchConfig, Integer> {

    BranchConfig findByBranchIdAndType(int branchId, VehicleType vehicleType);

    List<BranchConfig> findAllByBranchId(int branchId);
}
