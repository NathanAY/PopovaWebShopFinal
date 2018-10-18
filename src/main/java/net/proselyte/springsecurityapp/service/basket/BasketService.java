package net.proselyte.springsecurityapp.service.basket;

import net.proselyte.springsecurityapp.model.Basket;

import java.util.List;

public interface BasketService {

    void deleteByProductAndUser(Long productId, Long userId);

    void save(Basket basket);

    Basket findById(Long id);

    List<Basket> findAllByUser(Long user);

    List<Basket> findAll();
}
