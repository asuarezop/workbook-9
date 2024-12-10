package com.pluralsight.northwind.service;

import com.pluralsight.northwind.controllers.ProductDao;
import com.pluralsight.northwind.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UserInterface {
    //Related to input from user
    protected static String userInput;

    //Initializing scanner to read from terminal input
    protected static Scanner inputSc = new Scanner(System.in);

    //Boolean condition to exit application screens
    protected static boolean exitApp = false;

//    @Autowired
//    @Qualifier("simple")
//    ProductDao simpleProduct;

    @Autowired
    @Qualifier("jdbc")
    ProductDao jdbcProduct;

    public UserInterface() {
    }

    public void showHomeScreen() {
        String homeScreenMenu = """
                Select from the following options:
                
                [1] List Products
                [2] Add Products
                [X] Exit App
                """;

        do {
            System.out.println(homeScreenMenu);
            userInput = inputSc.nextLine().trim().toUpperCase();

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
//        List<Product> productList;
        List<Product> dbProductsList;

//        productList = simpleProduct.getAll();
        dbProductsList = jdbcProduct.getAll();

//        printProducts(productList);
        printProducts(dbProductsList);
    }

    protected void promptAddProducts() {
        Product p;

        System.out.println("Enter the following properties of product to add into list: ");
        String productId = promptUser("ID: ");
        int parsedProductId = Integer.parseInt(productId);

        String productName = promptUser("Product Name: ");
        String productCategory = promptUser("Product Category: ");
        String productPrice = promptUser("Product Price: ");
        double parsedProductPrice = Double.parseDouble(productPrice);

        p = new Product(parsedProductId, productName, productCategory, parsedProductPrice);

        //Calling the add() from SimpleProductDAO
//        simpleProduct.add(p);
        //Calling the add() from JdbcProductDAO
        jdbcProduct.add(p);
    }

    protected static String promptUser(String prompt) {
        System.out.print(prompt);
        return userInput = inputSc.nextLine().trim();
    }

    private void printProducts(List<Product> products) {
        if (!products.isEmpty()) {
            for (Product p : products) {
                System.out.printf("%d %s %s %.2f\n", p.getProductID(), p.getName(), p.getCategory(), p.getPrice());
            }
        }
    }
}
