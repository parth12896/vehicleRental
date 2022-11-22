package com.navi.vehicleRental.repository;

import com.navi.vehicleRental.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    default Branch create(Branch branch){
        if(findByName(branch.getName()) == null){
            return save(branch);
        }
        else {
            throw new EntityExistsException("Branch Already Exists");
        }
    }

    Branch findByName(String branchName);
}
