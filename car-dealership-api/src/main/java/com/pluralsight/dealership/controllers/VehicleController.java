package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.VehicleDAO;
import com.pluralsight.dealership.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Qualifier("vehicle")
@RequestMapping(path = "/vehicles")
public class VehicleController {

    @Autowired
    @Qualifier("vehicle-service")
    VehicleDAO vehicleManager;

    @RequestMapping(method = RequestMethod.GET)
    public List<Vehicle> findAllVehicles() {
        return vehicleManager.findAllVehicles();
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public List<Vehicle> findVehiclesByYear(Integer year) {
//        return vehicleManager.findVehiclesByYear(year);
//    }
}
