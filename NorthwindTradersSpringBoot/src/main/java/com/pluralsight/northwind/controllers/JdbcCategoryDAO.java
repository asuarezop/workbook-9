package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.interfaces.CategoryDao;
import com.pluralsight.northwind.models.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
@Qualifier("jdbc-category")
public class JdbcCategoryDAO implements CategoryDao {
    private DataSource dataSource;

    public JdbcCategoryDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(Category category) {

    }

    @Override
    public List<Category> getAll() {
        return List.of();
    }

    @Override
    public Category getCategoryByName(String name) {
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }
}
