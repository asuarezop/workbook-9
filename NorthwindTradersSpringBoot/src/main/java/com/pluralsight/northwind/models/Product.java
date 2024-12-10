package com.pluralsight.northwind.models;

public class Product {
    private int productID;
    private String name;
    private String category;
    private double price;

    public Product() {
    }

    public Product(int productID, String name, String category, double price) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
