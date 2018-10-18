package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    Product findByName(String name);

    Product findById(Long id);

    List<Product> findAllByNameContaining(String text);

    List<Product> findAll();

    void deleteById(Long id);

}