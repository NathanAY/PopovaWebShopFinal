package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {
    Order findById(Long id);

    void removeById(Long id);

    List<Order> findAllByUserId(Long user);

    List<Order> findAll();
}