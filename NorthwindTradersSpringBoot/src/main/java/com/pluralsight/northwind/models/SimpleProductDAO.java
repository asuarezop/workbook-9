package com.pluralsight.northwind.models;

import com.pluralsight.northwind.controllers.ProductDao;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

//Component for reading in products from memory
@Qualifier("simple")
public class SimpleProductDAO implements ProductDao {
    private List<Product> products = new ArrayList<>();

    public SimpleProductDAO() {}

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getAll() {
        return getProducts();
    }
}
