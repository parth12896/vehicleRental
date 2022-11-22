package com.navi.vehicleRental.api;

import com.navi.vehicleRental.DTO.OnboardBranchDTO;
import com.navi.vehicleRental.model.Branch;
import com.navi.vehicleRental.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/branch")
public class BranchApi {

    @Autowired
    private BranchService branchService;

    @GetMapping("/")
    public List<Branch> getAllBranch(){
        return branchService.getAll();
    }

    @PostMapping("/add")
    public boolean onboard(@RequestBody OnboardBranchDTO branch){
        return branchService.onboard(branch);
    }

}

