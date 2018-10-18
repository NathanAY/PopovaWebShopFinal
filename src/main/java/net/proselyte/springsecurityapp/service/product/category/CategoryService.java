package net.proselyte.springsecurityapp.service.product.category;

import net.proselyte.springsecurityapp.model.Category;
import net.proselyte.springsecurityapp.model.Product;

import java.util.List;

public interface CategoryService {

    void save(Category product);

    Category findByName(String name);

    Category findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);
}
