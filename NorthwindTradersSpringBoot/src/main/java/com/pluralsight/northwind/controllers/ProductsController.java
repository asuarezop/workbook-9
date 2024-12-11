package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.models.Product;
import com.pluralsight.northwind.service.UserInterface;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController implements ProductDao {
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getAll() {
        return getProducts();
    }

    @Override
    @RequestMapping(path = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {

        for (Product p: getProducts()) {
            if (p.getProductID() == id) {
                return p;
            }
        }

        return null;
    }
}
