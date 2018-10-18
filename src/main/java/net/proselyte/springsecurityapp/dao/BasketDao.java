package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Basket;
import net.proselyte.springsecurityapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketDao extends JpaRepository<Basket, Long> {
    Basket findById(Long id);

    void removeByProductAndUser(Long product, Long user);

    void deleteByProductAndUser(Long productId, Long userId);

    List<Basket> findAllByUser(Long user);

    List<Basket> findAll();
}