package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Category;
import net.proselyte.springsecurityapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Category findByName(String name);

    Category findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);
}