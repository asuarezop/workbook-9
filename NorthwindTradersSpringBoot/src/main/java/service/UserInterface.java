package service;

import java.util.Scanner;

public class UserInterface {

    //Related to input from user
    protected static String userInput;

    //Initializing scanner to read from terminal input
    protected static Scanner inputSc = new Scanner(System.in);

    //Boolean condition to exit application screens
    protected static boolean exitApp = false;

    public void showHomeScreen() {
        String homeScreenMenu = """
                Select from the following options:
                
                [1] List Products
                [2] Add Products
                [X] Exit App
                """;

        do {
            System.out.println(homeScreenMenu);
            userInput = inputSc.nextLine().trim();

            switch (userInput) {
                case "1":
                    promptListProducts();
                    break;
                case "2":
                    promptAddProducts();
                    break;
                case "X":
                    exitApp = true;
                    break;
                default:
                    System.out.println("Sorry, that's not a valid option. Please make your selection.");
            }

        } while (!exitApp);
    }

    protected void promptListProducts() {
        
    }

    protected void promptAddProducts() {

    }
}
