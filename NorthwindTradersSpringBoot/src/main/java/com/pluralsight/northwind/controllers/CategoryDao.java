package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.models.Category;

import java.util.List;

public interface CategoryDao {
    void add(Category category);
    List<Category> getAll();
    Category getCategoryByName(String name);
}
