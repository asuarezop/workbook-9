package com.pluralsight.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/products")
public class ProductController {
    List<Product> results = new ArrayList<>();

    public List<Product> getResults() {
        return results;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> findAll() {
        results.add(new Product(1, "Nike Air Jordan 4", "Sneakers", 135.00));
        return results;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Product findProductById(@PathVariable("id") int productId) {
        for (Product p : getResults()) {
            if (p.getProductId() == productId) {
                return new Product(1, "Nike Air Jordan 4", "Sneakers", 135.00);
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        System.out.println(product);
        return product;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int productId) {
        for (Product p : getResults()) {
            if (p.getProductId() == productId) {
                results.remove(p);
            }
        }
    }
}
