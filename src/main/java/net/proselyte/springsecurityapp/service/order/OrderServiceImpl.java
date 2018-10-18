package net.proselyte.springsecurityapp.service.order;

import net.proselyte.springsecurityapp.dao.BasketDao;
import net.proselyte.springsecurityapp.dao.OrderDao;
import net.proselyte.springsecurityapp.model.Basket;
import net.proselyte.springsecurityapp.model.Order;
import net.proselyte.springsecurityapp.service.basket.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            orderDao.removeById(id);
        } catch (Exception e) {

        }
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findAllByUserId(Long user) {
        return orderDao.findAllByUserId(user);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }
}
