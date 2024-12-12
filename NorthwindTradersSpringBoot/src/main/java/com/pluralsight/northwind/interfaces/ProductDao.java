package com.pluralsight.northwind.interfaces;

import com.pluralsight.northwind.models.Product;

import java.util.List;

public interface ProductDao {
    void add(Product product);
    List<Product> getAll();
    Product getProductById(int id);
}
