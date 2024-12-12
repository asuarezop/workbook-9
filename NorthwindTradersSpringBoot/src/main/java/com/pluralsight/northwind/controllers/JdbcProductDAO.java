package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.interfaces.ProductDao;
import com.pluralsight.northwind.models.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//For reading objects in the database
@Component
@Qualifier("jdbc-product")
public class JdbcProductDAO implements ProductDao {
    private DataSource dataSource;

    public JdbcProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        try (Connection conn = dataSource.getConnection()) {
            //Inserting a new product into Products table using the value from WHERE clause in Categories table
            PreparedStatement statement = conn.prepareStatement("""
                    INSERT INTO Products(ProductName, CategoryID, UnitPrice)
                    SELECT ?, ?, ?
                    FROM Categories
                    WHERE Categories.CategoryName = ?
                    """);
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getUnitPrice());
            statement.setString(4, product.getCategory());

            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        Product p;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT ProductID, ProductName, UnitPrice, Products.CategoryID, CategoryName FROM Products
                    JOIN Categories ON Products.CategoryID = Categories.CategoryID
                    """);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                double productPrice = rs.getDouble("UnitPrice");
                int productCategoryId = rs.getInt("CategoryID");
                String productCategory = rs.getString("CategoryName");

                p = new Product(productId, productName, productCategoryId, productCategory, productPrice);

                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public Product getProductById(int id) {
        Product p;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT ProductID, ProductName, UnitPrice, Products.CategoryID, CategoryName FROM Products
                    JOIN Categories ON Products.CategoryID = Categories.CategoryID
                    WHERE ProductID = ?
                    """);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                double productPrice = rs.getDouble("UnitPrice");
                int productCategoryId = rs.getInt("CategoryID");
                String productCategory = rs.getString("CategoryName");

                p = new Product(productId, productName, productCategoryId, productCategory, productPrice);

                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
