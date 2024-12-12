package com.pluralsight.northwind.models;

public class Product {
    private int productID;
    private String productName;
    private int categoryId;
    private String category;
    private double unitPrice;

    public Product(String productName, int categoryId, String category, double unitPrice) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public Product(int productID, String productName, int categoryId, String category, double unitPrice) {
        this.productID = productID;
        this.productName = productName;
        this.categoryId = categoryId;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
