package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.interfaces.ProductDao;
import com.pluralsight.northwind.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier("products")
@RequestMapping(path = "/products")
public class ProductsController {

    @Autowired
    @Qualifier("jdbc-product")
    ProductDao productDao;

    public void add(Product product) {
        productDao.add(product);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") Integer id) {
        return productDao.getProductById(id);
    }
}
