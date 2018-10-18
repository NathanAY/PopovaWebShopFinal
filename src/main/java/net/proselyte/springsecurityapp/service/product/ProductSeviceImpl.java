package net.proselyte.springsecurityapp.service.product;

import net.proselyte.springsecurityapp.dao.ProductDao;
import net.proselyte.springsecurityapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSeviceImpl implements ProductService{

    @Autowired
    ProductDao productDao;

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAllByNameContaining(String text) {
        return productDao.findAllByNameContaining(text);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productDao.deleteById(id);
    }
}
