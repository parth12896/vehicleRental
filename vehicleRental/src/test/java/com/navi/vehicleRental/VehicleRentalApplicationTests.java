package com.navi.vehicleRental;

import com.navi.vehicleRental.DTO.AddVehicleDTO;
import com.navi.vehicleRental.DTO.OnboardBranchDTO;
import com.navi.vehicleRental.model.Branch;
import com.navi.vehicleRental.model.VehicleType;
import com.navi.vehicleRental.service.BranchService;
import com.navi.vehicleRental.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class VehicleRentalApplicationTests {

	@Autowired BranchService branchService;
	@Autowired VehicleService vehicleService;

	@Test
	void AddBranch() {
		List<VehicleType> types = new ArrayList<>();
		types.add(VehicleType.valueOf("CAR"));
		types.add(VehicleType.valueOf("BIKE"));
		types.add(VehicleType.valueOf("VAN"));
		OnboardBranchDTO test = new OnboardBranchDTO("HSR",types );
		boolean actual = branchService.onboard(test);
		Assertions.assertTrue(actual);
	}

	@Test

	void AddVehicle(){
		Branch branch = new Branch();
		List<VehicleType> types = new ArrayList<>();
		types.add(VehicleType.valueOf("CAR"));
		types.add(VehicleType.valueOf("BIKE"));
		types.add(VehicleType.valueOf("VAN"));
		OnboardBranchDTO test = new OnboardBranchDTO("HSR",types );
		branchService.onboard(test);

		AddVehicleDTO vehicleDTO = new AddVehicleDTO();
		vehicleDTO.setVehicleName("Creta");
		vehicleDTO.setType(VehicleType.valueOf("CAR"));
		vehicleDTO.setHourlyPrice(3000);
		vehicleDTO.setRegNumber("KA01AA0000");
		vehicleDTO.setBranchName("HSR");
//
//		Mockito.when(branch.getInventoryList().stream()
//				.filter(inv -> inv.getVehicle().getType() == vehicleDTO.getType()).count()).thenReturn(2L);
		boolean actual = vehicleService.onboard(vehicleDTO);
		Assertions.assertTrue(actual);
	}

}
