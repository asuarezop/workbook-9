package com.pluralsight.northwind.interfaces;

import com.pluralsight.northwind.models.Category;

import java.util.List;

public interface CategoryDao {
    void add(Category category);
    List<Category> getAll();
    Category getCategoryByName(String name);
    Category getCategoryById(int id);
}
