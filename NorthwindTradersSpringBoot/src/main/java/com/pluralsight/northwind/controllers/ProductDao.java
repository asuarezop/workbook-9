package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.models.Product;

import java.util.List;


public interface ProductDao {
    void add(Product product);
    List<Product> getAll();
}
