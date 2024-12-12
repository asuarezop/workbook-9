package com.pluralsight.northwind.controllers;

import com.pluralsight.northwind.interfaces.CategoryDao;
import com.pluralsight.northwind.models.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    INSERT INTO Categories(CategoryName) VALUES
                    (?)
                    """);
            statement.setString(1, category.getCategoryName());

            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        Category c;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM Categories
                    """);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");

                c = new Category(categoryId, categoryName);
                categories.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public Category getCategoryByName(String name) {
        Category c;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM Categories
                    WHERE CategoryName = ?
                    """);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String categoryName = rs.getString("CategoryName");
                c = new Category(categoryName);

                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        Category c;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM Categories
                    WHERE CategoryID = ?
                    """);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
//                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");

                c = new Category(categoryName);

                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
