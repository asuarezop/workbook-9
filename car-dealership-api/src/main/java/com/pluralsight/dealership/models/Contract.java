package com.pluralsight.dealership.models;

//Abstract Contract Class - cannot be used to instantiate a new object
public abstract class Contract {
    private int id;
    private int vehicleVin;
    private String date;
    private String customerName;
    private String customerEmail;
//    private Vehicle vehicleSold;
    private double vehiclePrice;
    private double downPayment;

//    protected Contract(LocalDate date, String customerName, String customerEmail, Vehicle vehicleSold) {
//        this.date = date;
//        this.customerName = customerName;
//        this.customerEmail = customerEmail;
//        this.vehicleSold = vehicleSold;
//    }

    //For application representation
//    protected Contract(int id, LocalDate date, String customerName, String customerEmail, Vehicle vehicleSold) {
//        this.id = id;
//        this.date = date;
//        this.customerName = customerName;
//        this.customerEmail = customerEmail;
////        this.vehicleSold = vehicleSold;
//    }

    //For database representation
    protected Contract(int id, int vehicleVin, String date, String customerName, String customerEmail, double vehiclePrice, double downPayment) {
        this.id = id;
        this.date = date;
        this.vehicleVin = vehicleVin;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
    }

    public int getId() {
        return id;
    }
    public String getDate() {
        return date;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerEmail() {
        return customerEmail;
    }
//    public Vehicle getVehicleSold() {
//        return vehicleSold;
//    }
    public double getVehiclePrice() {
        return vehiclePrice;
    }
    public int getVehicleVin() {
        return vehicleVin;
    }
    public double getDownPayment() {
        return downPayment;
    }

    //Subclasses have to provide their own implementation of each of these abstract methods
    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();

    @Override
    public abstract String toString();
}
