package com.pluralsight.northwind.models;

import com.pluralsight.northwind.controllers.ProductDao;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcProductDAO implements ProductDao {
    private DataSource dataSource;
    @Override
    public void add(Product product) {

    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }
}
