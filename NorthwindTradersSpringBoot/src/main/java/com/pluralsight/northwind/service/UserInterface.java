package com.pluralsight.northwind.service;

import com.pluralsight.northwind.controllers.CategoriesController;
import com.pluralsight.northwind.controllers.ProductsController;
import com.pluralsight.northwind.models.Category;
import com.pluralsight.northwind.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class UserInterface {
    //Related to input from user
    protected String userInput;

    //Initializing scanner to read from terminal input
    protected Scanner inputSc = new Scanner(System.in);

    //Boolean condition to exit application screens
    protected boolean exitApp = false;

    @Autowired
    @Qualifier("products")
    ProductsController productRepo;

    @Autowired
    @Qualifier("categories")
    CategoriesController categoriesRepo;

//    @Autowired
//    @Qualifier("jdbc")
//    ProductDao jdbcProduct;

    public UserInterface() {
    }

    public void showHomeScreen() {
        String homeScreenMenu = """
                Select from the following options:
                
                [1] List Products
                [2] Add Products
                [3] Get Product By ID
                [4] List Categories
                [5] Add Categories
                [6] Get Categories By Name
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
                case "3":
                    promptGetProductById();
                    break;
                case "4":
                    promptListCategories();
                    break;
                case "5":
                    promptAddCategories();
                    break;
                case "6":
                    promptGetCategoriesByName();
                    break;
                case "X":
                    exitApp = true;
                    break;
                default:
                    System.out.println("Sorry, that's not a valid option. Please make your selection.");
            }
            System.out.println("exitApp state: " + exitApp); //verifying exitApp status
        } while (!exitApp);

        System.out.println("Exiting...");
    }

    protected void promptListProducts() {
        List<Product> productList;

        productList = productRepo.getAll();
        printProducts(productList);
    }

    protected void promptAddProducts() {
        Product p;

        System.out.println("Enter the following properties of product to add into list: ");

        String productId = promptUser("Product ID: ");
        int parsedProductId = Integer.parseInt(productId);

        String productName = promptUser("Product Name: ");
        String productCategoryName = promptUser("Product Category: ");

        String productCategoryId = promptUser("Product Category ID: ");
        int parsedProductCategoryId = Integer.parseInt(productCategoryId);

        String productPrice = promptUser("Product Price: ");
        double parsedProductPrice = Double.parseDouble(productPrice);

        p = new Product(parsedProductId, productName, parsedProductCategoryId, productCategoryName, parsedProductPrice);

        productRepo.add(p);
    }

    protected void promptGetProductById() {
        List<Product> foundProduct = new ArrayList<>();
        Product p;

        System.out.println("Please provide the product ID to filter for matching product: ");

        String productId = promptUser("ID: ");
        int parsedProductId = Integer.parseInt(productId);

        p = productRepo.getProductById(parsedProductId);
        foundProduct.add(p);
        printProducts(foundProduct);
    }

    protected void promptListCategories() {
        List<Category> categories;

        categories = categoriesRepo.getAll();
        printCategories(categories);
    }

    protected void promptAddCategories() {
        Category c;

        System.out.println("Enter the following category properties to add into list: ");

        String categoryId = promptUser("Category ID: ");
        int parsedCategoryId = Integer.parseInt(categoryId);

        String categoryName = promptUser("Category Name: ");

        c = new Category(parsedCategoryId, categoryName);

        categoriesRepo.add(c);
    }

    protected void promptGetCategoriesByName() {
        List<Category> foundCategory = new ArrayList<>();
        Category c;

        System.out.println("Please provide the category name to filter for matching category: ");
        String categoryName = promptUser("Name: ");

        c = categoriesRepo.getCategoryByName(categoryName);
        foundCategory.add(c);
        printCategories(foundCategory);
    }

    protected String promptUser(String prompt) {
        System.out.print(prompt);
        return userInput = inputSc.nextLine().trim();
    }

    protected void printProducts(List<Product> products) {
        if (!products.isEmpty()) {
            for (Product p : products) {
                System.out.printf("Product ID: %d, Product Name: %s, Category ID: %d, Category Name: %s, Unit Price: %.2f\n", p.getProductID(), p.getProductName(), p.getCategoryId(), p.getCategory(), p.getUnitPrice());
            }
        } else {
            System.out.println("No products matched your input.");
        }
    }

    protected void printCategories(List<Category> categories) {
        if (!categories.isEmpty()) {
            for (Category c : categories) {
                System.out.printf("Category ID: %d, Category Name: %s\n", c.getCategoryId(), c.getCategoryName());
            }
        } else {
            System.out.println("No categories matched your input.");
        }
    }
}
