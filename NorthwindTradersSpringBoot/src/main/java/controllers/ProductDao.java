package controllers;

import models.Product;

public interface ProductDao {
    void add(Product product);
    void getAll();
}
