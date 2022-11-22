package com.navi.vehicleRental.repository;

import com.navi.vehicleRental.model.BranchInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchInventoryRepository extends JpaRepository<BranchInventory, Integer> {
}
