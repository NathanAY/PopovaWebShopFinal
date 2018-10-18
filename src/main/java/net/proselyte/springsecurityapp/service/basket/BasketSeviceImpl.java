package net.proselyte.springsecurityapp.service.basket;

import net.proselyte.springsecurityapp.dao.BasketDao;
import net.proselyte.springsecurityapp.model.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BasketSeviceImpl implements BasketService {

    @Autowired
    BasketDao basketDao;

    @Override
    @Transactional
    public void deleteByProductAndUser(Long productId, Long userId) {
        try {
            basketDao.removeByProductAndUser(productId, userId);
        } catch (Exception e) {

        }
        basketDao.deleteByProductAndUser(productId, userId);
    }

    @Override
    public void save(Basket basket) {
        basketDao.save(basket);
    }

    @Override
    public Basket findById(Long id) {
        return basketDao.findById(id);
    }

    @Override
    public List<Basket> findAllByUser(Long user) {
        return basketDao.findAllByUser(user);
    }

    @Override
    public List<Basket> findAll() {
        return basketDao.findAll();
    }
}
