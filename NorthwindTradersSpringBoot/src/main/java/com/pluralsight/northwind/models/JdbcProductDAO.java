package com.pluralsight.northwind.models;

import com.pluralsight.northwind.controllers.ProductDao;
import org.apache.commons.dbcp2.BasicDataSource;
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
@Qualifier("jdbc")
public class JdbcProductDAO implements ProductDao {
    private DataSource dataSource;

    public JdbcProductDAO (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
//        products.add(product);
        try(Connection conn = dataSource.getConnection()) {
            //Setting the CategoryID from Products table using the value from WHERE clause in Categories table
            PreparedStatement statement = conn.prepareStatement("""
                    INSERT INTO Products(ProductName, CategoryID, UnitPrice)
                    SELECT ?, Categories.CategoryID, ?
                    FROM Categories
                    WHERE CategoryName = ?
                    """);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getCategory());

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
                    SELECT ProductID, ProductName, UnitPrice, CategoryName FROM Products
                    JOIN Categories ON Products.CategoryID = Categories.CategoryID
                    """);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                double productPrice = rs.getDouble("UnitPrice");
                String productCategory = rs.getString("CategoryName");

                p = new Product(productName, productCategory, productPrice);

                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
