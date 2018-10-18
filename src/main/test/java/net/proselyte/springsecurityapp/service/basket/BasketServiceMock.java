package net.proselyte.springsecurityapp.service.basket;

import net.proselyte.springsecurityapp.model.Basket;

import java.util.ArrayList;
import java.util.List;

public class BasketServiceMock implements BasketService {

    private final List<Basket> baskets = new ArrayList<>();

    @Override
    public void deleteByProductAndUser(Long productId, Long userId) {
        List<Basket> toRemove = new ArrayList<>();
        for (final Basket basket :  baskets) {
            if (basket.getProduct() == productId && basket.getUser() == userId) {
                toRemove.add(basket);
            }
        }
        baskets.removeAll(toRemove);
    }

    @Override
    public void save(Basket basket) {
        this.baskets.add(basket);
    }

    @Override
    public Basket findById(Long id) {
        return null;
    }

    @Override
    public List<Basket> findAllByUser(Long user) {
        List<Basket> result = new ArrayList<>();
        for (Basket basket : baskets) {
            if(basket.getUser() == user) {
                result.add(basket);
            }
        }
        return result;
    }

    @Override
    public List<Basket> findAll() {
        return baskets;
    }
}
