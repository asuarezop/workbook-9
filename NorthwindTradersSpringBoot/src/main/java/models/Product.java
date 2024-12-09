package models;

public class Product {
    private int productID;
    private String name;
    private String category;
    private double price;

    public Product() {
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
