package models;

import controllers.ProductDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleProductDAO implements ProductDao {
    private List<Product> products;

    public SimpleProductDAO() {
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public void getAll() {

    }
}
