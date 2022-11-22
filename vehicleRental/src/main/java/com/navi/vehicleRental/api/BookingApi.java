package com.navi.vehicleRental.api;

import com.navi.vehicleRental.DTO.BookingDTO;
import com.navi.vehicleRental.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookingApi {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/")
    public int placeBooking(@RequestBody BookingDTO bookingDTO){
        return bookingService.placeOrder(bookingDTO);
    }


}
