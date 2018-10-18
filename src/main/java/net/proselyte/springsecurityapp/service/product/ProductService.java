package net.proselyte.springsecurityapp.service.product;

import net.proselyte.springsecurityapp.model.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);

    Product findByName(String name);

    Product findById(Long id);

    List<Product> findAllByNameContaining(String text);

    List<Product> findAll();

    void deleteById(Long id);
}
