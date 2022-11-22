package com.navi.vehicleRental.service;

import com.navi.vehicleRental.DTO.OnboardBranchDTO;
import com.navi.vehicleRental.model.Branch;
import com.navi.vehicleRental.model.BranchConfig;
import com.navi.vehicleRental.repository.BranchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BranchService {

    @Autowired
    private BranchRepository branchRepo;
    @Autowired
    private BranchConfigService branchConfigService;

    public List<Branch> getAll(){
        return branchRepo.findAll();
    }

    public boolean onboard(OnboardBranchDTO branchDTO){
        //First add Branch
        Branch updatedBranch;
        try {
            Branch newBranch = new Branch();
            newBranch.setName(branchDTO.getBranchName());
            updatedBranch = branchRepo.create(newBranch);
        } catch (Exception e){
            log.error("Branch creation failed {}", e.getMessage());
            return false;
        }

        //Add Type of Vehicle and define capacity
        branchDTO.getAllowedTypes().forEach(
                vehicleType -> {
                    BranchConfig newBranchConfig = new BranchConfig();
                    newBranchConfig.setBranch(updatedBranch);
                    newBranchConfig.setType(vehicleType);
                    branchConfigService.add(newBranchConfig);
                }
            );

        return true;
    }

    public Branch findByName(String branchName){
        return branchRepo.findByName(branchName);
    }

}
