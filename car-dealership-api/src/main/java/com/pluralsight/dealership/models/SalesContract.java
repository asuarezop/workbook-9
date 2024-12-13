package com.pluralsight.dealership.models;

import java.time.LocalDate;
import java.util.Date;

public class SalesContract extends Contract {
    private final double salesTax = 0.05;
    private final double recordingFee = 100.00;
    private final double processingFee = 0.00;
    private boolean isFinanced;
    private double downPayment;
    private double monthlyPayment;

    //For application representation
//    public SalesContract(LocalDate date, String customerName, String customerEmail, Vehicle vehicleSold) {
//        super(date, customerName, customerEmail, vehicleSold);
//    }

    public SalesContract(int id, int vin, Date date, String customerName, String customerEmail, double vehiclePrice) {
        super(id, vin, date, customerName, customerEmail, vehiclePrice);
    }

    public double getSalesTax() {
        return salesTax * getVehiclePrice();
    }
    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        if (getVehiclePrice() < 10000) {
            return processingFee + 295.00;
        } else {
            return processingFee + 495.00;
        }
    }

    public boolean isFinanced() {
        return isFinanced;
    }
    public double getDownPayment() {
        return downPayment;
    }

    @Override
    public double getTotalPrice() {
        return getVehiclePrice() + getSalesTax() + getRecordingFee() + getProcessingFee();
    }

    @Override
    public double getMonthlyPayment() {
        double interestRate;
        double loanAmount;
        int loanTerm;

        if (!isFinanced) {
            monthlyPayment = 0.0;

            return monthlyPayment;
        }
        //For financing option
        if (getVehiclePrice() >= 10000) {
            //Interest Rate = 4.25%
            interestRate = (4.25 / 12) / 100;
            loanAmount = getVehiclePrice() - getDownPayment();
            loanTerm = 48;
        } else {
            //Interest Rate = 5.25%
            interestRate = (5.25 / 12) / 100;
            loanAmount = getVehiclePrice() - getDownPayment();
            loanTerm = 24;
        }

        monthlyPayment = (loanAmount * interestRate * Math.pow(1 + interestRate, loanTerm)) / (Math.pow(1 + interestRate, loanTerm) - 1);
        return monthlyPayment;
    }

    @Override
    public String toString() {
        return String.format("%-12s %-16s %-27s %-9s %8.2f %15.2f %16.2f %13.2f %7s %20.2f", getDate(), getCustomerName(), getCustomerEmail(), getVehicleVin(), getSalesTax(), getRecordingFee(), getProcessingFee(), getTotalPrice(), (isFinanced() ? "YES" : "NO"), getMonthlyPayment());
    }
}
