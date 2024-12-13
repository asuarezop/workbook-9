package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.VehicleDAO;
import com.pluralsight.dealership.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier("vehicle")
@RequestMapping(path = "/vehicles")
public class VehicleController {

    @Autowired
    @Qualifier("vehicle-service")
    VehicleDAO vehicleManager;

    @GetMapping
    public List<Vehicle> findAllVehicles() {
        return vehicleManager.findAllVehicles();
    }

    @RequestMapping(path = "/find_price_range/", method = RequestMethod.GET)
    public List<Vehicle> findVehiclesByPriceRange(@RequestParam Double min, Double max) {
        return vehicleManager.findVehiclesByPriceRange(min, max);
    }

    @RequestMapping(path = "/find_make_model/", method = RequestMethod.GET)
    public List<Vehicle> findVehiclesByMakeModel(@RequestParam String make, String model) {
        return vehicleManager.findVehiclesByMakeModel(make, model);
    }

    @RequestMapping(path = "/find_year/", method = RequestMethod.GET)
    public List<Vehicle> findVehiclesByYear(@RequestParam Integer year) {
        return vehicleManager.findVehiclesByYear(year);
    }

    @RequestMapping(path = "/find_color/", method = RequestMethod.GET)
    public List<Vehicle> findVehiclesByColor(@RequestParam String color) {
        return vehicleManager.findVehiclesByColor(color);
    }

    @RequestMapping(path = "/find_mileage_range/", method = RequestMethod.GET)
    public List<Vehicle> findVehiclesByMileage(Integer min, Integer max) {
        return vehicleManager.findVehiclesByMileage(min, max);
    }

    @RequestMapping(path = "/find_type/", method = RequestMethod.GET)
    public List<Vehicle> findVehiclesByVehicleType(@RequestParam String type) {
        return vehicleManager.findVehiclesByVehicleType(type);
    }
}
