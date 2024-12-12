package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.interfaces.CategoryDao;
import com.pluralsight.northwind.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoriesController {

    @Autowired
    @Qualifier("jdbc-category")
    CategoryDao categoryDao;

    public void add(Category category) {
        categoryDao.add(category);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public Category getCategoryByName(@PathVariable("name") String name) {
        return categoryDao.getCategoryByName(name);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable("id") Integer id) {
        return categoryDao.getCategoryById(id);
    }
}
