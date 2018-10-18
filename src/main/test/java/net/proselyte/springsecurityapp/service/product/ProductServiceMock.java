package net.proselyte.springsecurityapp.service.product;

import net.proselyte.springsecurityapp.model.Product;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Ignore
public class ProductServiceMock implements ProductService {

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private List<Product> products = new ArrayList<>();

    @Override
    public void save(Product product) {

    }

    @Override
    public Product findByName(String name) {
        return null;
    }

    @Override
    public Product findById(Long id) {
        for (Product product: products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> findAllByNameContaining(String text) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void deleteById(Long id) {

    }
}
