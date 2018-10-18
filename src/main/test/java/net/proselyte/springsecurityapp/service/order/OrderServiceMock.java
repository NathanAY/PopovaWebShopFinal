package net.proselyte.springsecurityapp.service.order;

import net.proselyte.springsecurityapp.model.Order;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.List;

@Ignore
public class OrderServiceMock implements OrderService {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Order order) {
        this.orders.add(order);
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAllByUserId(Long user) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }
}
