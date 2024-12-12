package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.models.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Qualifier("categories")
@RequestMapping(path = "/categories")
public class CategoriesController implements CategoryDao {
    private List<Category> categories = new ArrayList<>();

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public void add(Category category) {
        categories.add(category);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAll() {
        return getCategories();
    }

    @Override
    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public Category getCategoryByName(@PathVariable("name") String name) {

        for (Category c : getCategories()) {
            if (c.getCategoryName().equalsIgnoreCase(name)) {
                return c;
            }
        }

        return null;
    }
}
