package com.pluralsight.dealership.models;

import java.time.LocalDate;


public class LeaseContract extends Contract {
    private int id;
    private final double expectedEndValue = 0.50;
    private final double leaseFee = 0.07;
    private double downPayment;
    private double monthlyPayment;

    public LeaseContract(int id, int vin, LocalDate date, String customerName, String customerEmail, double vehiclePrice) {
        super(id, vin, date, customerName, customerEmail, vehiclePrice);
    }

    @Override
    public int getId() {
        return id;
    }

    public double getExpectedEndValue() {
        return expectedEndValue * getVehiclePrice();
    }
    public double getLeaseFee() {
        return leaseFee * getVehiclePrice();
    }
    public double getDownPayment() {
        return downPayment;
    }

    @Override
    public double getTotalPrice() {
        return getExpectedEndValue() + getLeaseFee() + 100.00;
    }

    @Override
    public double getMonthlyPayment() {
        int leaseTerm = 36;
        //Money factor formula = 4/2400  --> Interest rate of 4% / 2400
        double moneyFactor = (double) 4 / 2400;
        //Adjusted Capitalized Cost formula = Original price of vehicle - down payment
        double adjustedCapitalizedCost = getVehiclePrice() - getDownPayment();
        //Estimated value of an asset at the end of the lease term
        double residualValue = getExpectedEndValue();
        //The loss of value in asset (vehicle) over the lease term
        double depreciatingCost = (adjustedCapitalizedCost - residualValue) / leaseTerm;
        //Charges associated with purchasing vehicle (includes interest)
        double financeCost = (getVehiclePrice() + residualValue) * moneyFactor;

        monthlyPayment = depreciatingCost + financeCost + getLeaseFee();

        return monthlyPayment;
    }

    @Override
    public String toString() {
        return String.format("%-12s %-18s %-27s %-10s %11.2f %20.2f %16.2f %20.2f", getDate(), getCustomerName(), getCustomerEmail(), getVehicleVin(), getExpectedEndValue(), getLeaseFee(), getTotalPrice(), getMonthlyPayment());
    }
}
