package com.pluralsight.dealership.interfaces;

import com.pluralsight.dealership.models.Vehicle;

import java.util.List;

public interface VehicleDAO {
    List<Vehicle> findAllVehicles();
    List<Vehicle> findVehiclesByPriceRange(double min, double max);
    List<Vehicle> findVehiclesByMakeModel(String make, String model);
    List<Vehicle> findVehiclesByYear(int year);
    List<Vehicle> findVehiclesByColor(String color);
    List<Vehicle> findVehiclesByMileage(int min, int max);
    List<Vehicle> findVehiclesByVehicleType(String type);
    void addVehicleToInventory(Vehicle v);
    void removeVehicleFromInventory(Vehicle v);
    void updateVehicleFromInventory(boolean status, Vehicle v);
    Vehicle findVehicleByVin(int vin);
    int findVehicleVinByContractId(int id);
}
