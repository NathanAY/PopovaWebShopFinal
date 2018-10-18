package net.proselyte.springsecurityapp.service.order;

import net.proselyte.springsecurityapp.model.Basket;
import net.proselyte.springsecurityapp.model.Order;

import java.util.List;

public interface OrderService {

    void deleteById(Long id);

    void save(Order order);

    Order findById(Long id);

    List<Order> findAllByUserId(Long user);

    List<Order> findAll();
}
