package com.navi.vehicleRental.service;

import com.navi.vehicleRental.model.BranchConfig;
import com.navi.vehicleRental.model.VehicleType;
import com.navi.vehicleRental.repository.BranchConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchConfigService {
    @Autowired
    private BranchConfigRepository branchConfigRepository;

    public List<BranchConfig> getAll(){
        return branchConfigRepository.findAll();
    }

    public BranchConfig add(BranchConfig branchConfig){
        return branchConfigRepository.save(branchConfig);
    }

    public int getCapacity(int branchId, VehicleType type){
        BranchConfig storage = branchConfigRepository.findByBranchIdAndType(branchId, type);
        if (storage != null)
            return storage.getCapacity();
        return -1;
    }
}
