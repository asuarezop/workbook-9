package com.pluralsight.dealership.services;

import JavaHelpers.ColorCodes;
import com.pluralsight.dealership.controllers.DealershipController;
import com.pluralsight.dealership.controllers.VehicleController;
import com.pluralsight.dealership.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UserInterface {
    //Data manager service objects
    @Autowired
    @Qualifier("dealership")
    protected DealershipController dealershipRepo;

    @Autowired
    @Qualifier("vehicle")
    protected VehicleController vehicleRepo;

//    @Autowired
//    SalesContractService salesManager;
//
//    @Autowired
//    LeaseContractService leaseManager;

    //Related to input from user
    protected static String userInput;

    //Initializing scanner to read from terminal input
    protected static Scanner inputSc = new Scanner(System.in);

    //Dealership selected by user based on id
    protected int dealershipChoice;

    //Boolean condition to exit application screens
    protected boolean exitApp = false;

    public void showHomeScreen() {
        String homeScreenMenuHeader = ColorCodes.LIGHT_BLUE + """
                =================================
                |      DEALERSHIP APP (HOME)    |
                =================================
                """ + ColorCodes.RESET;
        String prompt = """
                \nPlease select what type of request to filter from dealership inventory:
                
                [1] Price - filter vehicles within a price range
                [2] Make Model - filter vehicles by make/model
                [3] Year - filter vehicles by year range
                [4] Color - filter vehicles by color
                [5] Mileage - filter vehicles by mileage range
                [6] Vehicle Type - filter vehicles by type (SUV, Sedan, Hatchback, etc.)
                [7] All Vehicles - display every vehicle from inventory
                [8] Add Vehicle - adds a new vehicle to inventory
                [9] Remove Vehicle - removes a vehicle from inventory
                [10] Sell/Lease Vehicle - select vehicle to put up for sale/lease in a contract
                [A] Run Admin Panel - enter the application as admin to view additional queries
                [X] Exit Application - quits running application
                """;

        do {
            System.out.println(homeScreenMenuHeader);

            //Grabbing selected dealership from user
            Dealership d = promptDealership();
            System.out.println(d);

            System.out.println(prompt);
            userInput = inputSc.nextLine().trim().toUpperCase();

            switch (userInput) {
                case "1":
                    processGetByPriceRequest(d);
                    break;
                case "2":
                    processGetByMakeModelRequest(d);
                    break;
                case "3":
                    processGetByYearRequest(d);
                    break;
                case "4":
                    processGetByColorRequest(d);
                    break;
                case "5":
                    processGetByMileageRequest(d);
                    break;
                case "6":
                    processGetByVehicleTypeRequest(d);
                    break;
                case "7":
                    processGetAllVehiclesRequest(d);
                    break;
                case "8":
//                    processAddVehicleRequest(d);
                    break;
                case "9":
//                    processRemoveVehicleRequest(d);
                    break;
                case "10":
//                    processSellLeaseVehicleRequest(d);
                    break;
                case "A":
//                    runAsAdmin(d);
                    break;
                case "X":
                    exitApp = true;
                    break;
                default:
                    System.out.println("Sorry, that's not a valid option. Please make your selection.");
            }
        } while (!exitApp);

        System.out.println("Exiting...");
    }

    //Other non-static methods to process user requests
    public void processGetByPriceRequest(Dealership dealership) {
        promptInstructions("Enter your desired price range to search vehicles from:  " + dealership.getName());

        String min = promptUser("Minimum value: ");
        double minPrice = Double.parseDouble(min);

        String max = promptUser("Maximum value: ");
        double maxPrice = Double.parseDouble(max);

        List<Vehicle> vehicles = vehicleRepo.findVehiclesByPriceRange(minPrice, maxPrice);
        printVehicleList(vehicles);
    }

    public void processGetByMakeModelRequest(Dealership dealership) {
        promptInstructions("Enter vehicle make and model to search vehicles from:  " + dealership.getName());
        String vehicleMake = promptUser("Make: ");
        String vehicleModel = promptUser("Model: ");

        if (!vehicleMake.isEmpty() && !vehicleModel.isEmpty()) {
            List<Vehicle> vehicles = vehicleRepo.findVehiclesByMakeModel(vehicleMake, vehicleModel);
            printVehicleList(vehicles);
        } else {
            System.out.println("No vehicles matched your provided make/model. Please try again.");
        }
    }

    public void processGetByYearRequest(Dealership dealership) {
        promptInstructions("Enter vehicle year to search vehicles from:  " + dealership.getName());
        String vehicleYear = promptUser("Year: ");
        int year = Integer.parseInt(vehicleYear);

        String parsedYear = String.valueOf(year);

        //Checking length of String parsedYear is not greater than 4
        if (year != 0 && parsedYear.length() == 4) {
            List<Vehicle> vehicles = vehicleRepo.findVehiclesByYear(year);
            printVehicleList(vehicles);
        } else {
            System.out.println("No vehicles matched given year. Please try again.");
        }
    }

    public void processGetByColorRequest(Dealership dealership) {
        promptInstructions("Enter vehicle color to search vehicles from:  " + dealership.getName());
        String vehicleColor = promptUser("Color: ");

        if (!vehicleColor.isEmpty()) {
            List<Vehicle> vehicles = vehicleRepo.findVehiclesByColor(vehicleColor);
            printVehicleList(vehicles);
        } else {
            System.out.println("No vehicles found that match given color. Please try again.");
        }
    }

    public void processGetByMileageRequest(Dealership dealership) {
        promptInstructions("Enter your desired mileage range to search vehicles from:  " + dealership.getName());
        String min = promptUser("Minimum mileage: ");
        int minMileage = Integer.parseInt(min);

        String max = promptUser("Maximum mileage: ");
        int maxMileage = Integer.parseInt(max);

        if (minMileage != 0 && maxMileage != 0) {
            List<Vehicle> vehicles = vehicleRepo.findVehiclesByMileage(minMileage, maxMileage);
            printVehicleList(vehicles);
        } else {
            System.out.println("No vehicles found that match provided mileage range. Please try again.");
        }
    }

    public void processGetByVehicleTypeRequest(Dealership dealership) {
        promptInstructions("Enter vehicle type to search vehicles from:  " + dealership.getName());
        String vehicleType = promptUser("Type: ");

        if (!vehicleType.isEmpty()) {
            List<Vehicle> vehicles = vehicleRepo.findVehiclesByVehicleType(vehicleType);
            printVehicleList(vehicles);
        } else {
            System.out.println("Invalid vehicle type. Please try again.");
        }
    }

    public void processGetAllVehiclesRequest(Dealership dealership) {
        promptInstructions("Inventory for:  " + dealership.getName());

        List<Vehicle> vehicles = vehicleRepo.findAllVehicles();
        printVehicleList(vehicles);
    }
//
//    public void processAddVehicleRequest(Dealership dealership) {
//        Vehicle v;
//        promptInstructions("Enter new vehicle to add into:  " + dealership.getName());
//
//        String usedVehicleVIN = promptUser("VIN: ");
//        int parsedUsedVehicleVIN = Integer.parseInt(usedVehicleVIN);
//
//        String usedVehicleYear = promptUser("Year: ");
//        int parsedUsedVehicleYear = Integer.parseInt(usedVehicleYear);
//
//        String usedVehicleMake = promptUser("Make: ");
//        String usedVehicleModel = promptUser("Model: ");
//        String usedVehicleType = promptUser("Type: ");
//        String usedVehicleColor = promptUser("Color: ");
//
//        String usedVehicleMileage = promptUser("Mileage: ");
//        int parsedUsedVehicleMileage = Integer.parseInt(usedVehicleMileage);
//
//        String usedVehiclePrice = promptUser("Price: ");
//        double parsedUsedVehiclePrice = Double.parseDouble(usedVehiclePrice);
//
//        boolean parsedHasVehicleSold = promptVehicleSold("Has vehicle been sold: ");
//
//        v = new Vehicle(parsedUsedVehicleVIN, parsedUsedVehicleYear, usedVehicleMake, usedVehicleModel, usedVehicleType, usedVehicleColor, parsedUsedVehicleMileage, parsedUsedVehiclePrice, parsedHasVehicleSold);
//
//        vehicleManager.addVehicleToInventory(v);
//    }
//
//    public void processRemoveVehicleRequest(Dealership dealership) {
//        Vehicle v;
//        promptInstructions("Enter desired vehicle you wish to remove from:  " + dealership.getName());
//        String vehicleVin = promptUser("VIN: ");
//        int parsedVehicleVin = Integer.parseInt(vehicleVin);
//
//        v = new Vehicle(parsedVehicleVin);
//
//        vehicleManager.removeVehicleFromInventory(v);
//    }
//
//    public void processSellLeaseVehicleRequest(Dealership dealership) {
//        Vehicle v;
//        promptInstructions("Would you like to sell or lease vehicle?:  ");
//        String contractOption = promptUser("""
//                [1] Sell
//                [2] Lease
//                """);
//        int parsedContractOption = Integer.parseInt(contractOption);
//        promptInstructions("Enter the VIN of the vehicle to put in sale/lease contract from:  " + dealership.getName());
//        String selectedVehicle = promptUser("VIN: ");
//        int parsedSelectedVehicle = Integer.parseInt(selectedVehicle);
//
//        v = vehicleManager.findVehicleByVin(parsedSelectedVehicle);
//
//        if (parsedContractOption == 1) {
//            promptContractDetails("sale" , v);
//        } else if (parsedContractOption == 2) {
//            promptContractDetails("lease", v);
//        }
//    }
//
//    private void runAsAdmin(Dealership dealership) {
//        promptInstructions("Enter password to enter admin portal:  ");
//        String appPassword = promptUser("Password: ");
//
//        if (appPassword.equals("admin")) {
//            AdminUserInterface adminUI = new AdminUserInterface();
//
//            //Display admin UI options
//            adminUI.showAdminScreen(dealership);
//        }
//
//        //If user typed wrong password, go back to regular screen
//        showHomeScreen();
//    }

    //Retrieves dealerships from database
    public Dealership promptDealership() {
        //Querying for all dealerships
        List<Dealership> dealerships = dealershipRepo.findAllDealerships();

        printDealershipList(dealerships);

        //Prompting for user dealership choice
        promptInstructions("\nEnter dealership to search from:  ");
        dealershipChoice = inputSc.nextInt();
        inputSc.nextLine();

        return dealershipRepo.findDealershipById(dealershipChoice);
    }

    //Retrieves user input from a prompt
    protected static String promptUser(String prompt) {
        System.out.print(ColorCodes.WHITE + prompt + ColorCodes.RESET);
        return userInput = inputSc.nextLine().trim();
    }

    protected static void promptInstructions(String prompt) {
        String[] textDetails = prompt.split(": ");
        System.out.println(ColorCodes.LIGHT_BLUE + textDetails[0] + ColorCodes.ORANGE_BOLD + ColorCodes.ITALIC + textDetails[1] + ColorCodes.RESET);
    }

    public static boolean promptVehicleSold(String prompt) {
        String hasVehicleBeenSold = promptUser(prompt);
        return hasVehicleBeenSold.equalsIgnoreCase("Yes") | hasVehicleBeenSold.equals("1");
    }

//    public void promptContractDetails(String contractType, Vehicle vehicle) {
//        SalesContract vehicleSale;
//        LeaseContract vehicleLease;
//        LocalDateTime contractDate = LocalDateTime.now();
//        String[] contractPrompts = {"Enter the customer name associated with the " + contractType + ":  ", "Enter the customer email associated with the " + contractType + ":  ", "Enter whether the vehicle was financed or not:  ", "How much would you like to put towards down payment?:  "};
//        String[] userInputPrompts = {"Customer name: ", "Customer email: ", """
//                    [1] Yes
//                    [2] No
//                    """, "Down payment amount: "};
//
//        //Retrieving LocalDate for contract
//        LocalDate dateOfContract = LocalDate.parse(DateHandler.getContractDate(contractDate));
//
//        //Prompting for customer name
//        promptInstructions(contractPrompts[0]);
//        String customerName = promptUser(userInputPrompts[0]);
//
//        //Prompting for customer email
//        promptInstructions(contractPrompts[1]);
//        String customerEmail = promptUser(userInputPrompts[1]);
//
//        if (contractType.equals("sale")) {
//            //Prompting to determine if vehicle was financed
//            promptInstructions(contractPrompts[2]);
//            String financedOption = promptUser(userInputPrompts[2]);
//
//            //Passing in sales data from the user into new SalesContract object
//            vehicleSale = new SalesContract(dateOfContract, customerName, customerEmail, vehicle);
//
//            if (financedOption.equals("1")) {
//                //Setting isFinanced boolean variable to true in SalesContract
//                vehicleSale.setFinanced(true);
//
//                //Prompting to determine down payment for vehicle
//                promptInstructions(contractPrompts[3]);
//                double downPayment = Double.parseDouble(promptUser(userInputPrompts[3]));
//
//                //Setting down payment variable to user's amount for SalesContract
//                vehicleSale.setDownPayment(downPayment);
//
//            } else if (financedOption.equals("2")) {
//                //Setting isFinanced boolean variable to false in SalesContract
//                vehicleSale.setFinanced(false);
//
//                //Indicating user intends to pay with cash
//                vehicleSale.setDownPayment(0);
//            }
//
//            //Saving SalesContract data to database
//            salesManager.saveSalesContract(vehicleSale);
//
//        } else if (contractType.equals("lease")) {
//            //Passing in lease data from the user into new LeaseContract object
//            vehicleLease = new LeaseContract(dateOfContract, customerName, customerEmail, vehicle);
//
//            //Prompting to determine down payment for vehicle
//            promptInstructions(contractPrompts[3]);
//            double downPayment = Double.parseDouble(promptUser(userInputPrompts[3]));
//
//            //Setting down payment variable to user's amount for LeaseContract
//            vehicleLease.setDownPayment(downPayment);
//
//            //Saving LeaseContract data to database
//            leaseManager.saveLeaseContract(vehicleLease);
//        }
//
//        //Confirmation message
//        System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Contract has been saved." + ColorCodes.RESET);
//    }

    public static void printDealershipHeader() {
        String dealershipHeader = ColorCodes.ORANGE_UNDERLINED + String.format("%-5s %-19s %-10s %38s", "ID", "Name", "Address", "Phone") + ColorCodes.RESET;
        System.out.println(dealershipHeader);
    }

    public static void printVehicleHeader() {
        String vehicleHeader = ColorCodes.LIGHT_BLUE_UNDERLINED + String.format("%-10s %-8s %-12s %-18s %-12s %-10s %-12s %-12s %-2s", "VIN", "Year", "Make", "Model", "Type", "Color", "Odometer", "Price", "Sold") + ColorCodes.RESET;
        System.out.println(vehicleHeader);
    }

//    public static <T extends Contract> void printContractHeader(List<T> contracts) {
//        String contractHeader;
//
//        //Retrieving the type of subclass for contracts list
//        Contract contractType = contracts.get(0);
//
//        if (contractType instanceof SalesContract) {
//            contractHeader = ColorCodes.CYAN_UNDERLINED + String.format("%-12s %-16s %-27s %-7s %10s %15s %16s %13s %10s %17s", "Date", "Customer Name", "Customer Email", "VIN", "Sales Tax", "Recording Fee", "Processing Fee", "Total Price", "Financed", "Monthly Payment") + ColorCodes.RESET;
//            System.out.println(contractHeader);
//        } else if (contractType instanceof LeaseContract) {
//            contractHeader = ColorCodes.CYAN_UNDERLINED + String.format("%-12s %-18s %-27s %-8s %10s %15s %16s %20s", "Date", "Customer Name", "Customer Email", "VIN", "Expected End Value", "Lease Fee", "Total Price", "Monthly Payment") + ColorCodes.RESET;
//            System.out.println(contractHeader);
//        } else {
//            System.out.println("Unknown Contract");
//        }
//    }

    protected static void printDealershipList(List<Dealership> dealerships) {
        if (!dealerships.isEmpty()) {
            printDealershipHeader();
            for (Dealership d: dealerships) {
                System.out.println(d);
            }
        } else {
            System.out.println("No dealerships matched your input.");
        }
    }

    protected static void printVehicleList(List<Vehicle> vehicles) {
        if (!vehicles.isEmpty()) {
            printVehicleHeader();
            for (Vehicle v : vehicles) {
                System.out.println(v);
            }
        } else {
            System.out.println("No vehicles matched your input.");
        }
    }
//
//    protected static <T extends Contract> void printContractList(List<T> contracts) {
//        if (!contracts.isEmpty()) {
//            printContractHeader(contracts);
//            for (Contract c: contracts) {
//                System.out.println(c);
//            }
//        } else {
//            System.out.println("No contracts matched your input.");
//        }
//    }
}
