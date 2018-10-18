package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.Basket;
import net.proselyte.springsecurityapp.model.Product;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.UserServiceMock;
import net.proselyte.springsecurityapp.service.basket.BasketServiceMock;
import net.proselyte.springsecurityapp.service.product.ProductServiceMock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class UserControllerTestNG {

    private final UserController userController = new UserController();
    private final BasketServiceMock basketServiceMock = new BasketServiceMock();

    @BeforeTest
    public void setUp() {
        UserServiceMock userServiceMock = new UserServiceMock();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("name1");
        userServiceMock.save(user1);
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("name2");
        userServiceMock.save(user2);
        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("name3");
        userServiceMock.save(user3);

        Basket basket1 = new Basket();
        basket1.setProduct(1L);
        basket1.setUser(1L);
        Basket basket2 = new Basket();
        basket2.setProduct(2L);
        basket2.setUser(1L);
        Basket basket3 = new Basket();
        basket3.setProduct(2L);
        basket3.setUser(2L);
        basketServiceMock.save(basket1);
        basketServiceMock.save(basket2);
        basketServiceMock.save(basket3);

        ProductServiceMock productServiceMock = new ProductServiceMock();
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setName("product1");
        product.setId(1L);
        productList.add(product);
        Product product1 = new Product();
        product1.setName("product2");
        product1.setId(3L);
        productList.add(product1);
        Product product2 = new Product();
        product2.setName("product3");
        product2.setId(3L);
        productList.add(product2);
        productServiceMock.setProducts(productList);

        userController.setService(productServiceMock, "product");
        userController.setService(userServiceMock, "user");
        userController.setService(basketServiceMock, "basket");
    }

    @Test
    public void removeFromBasket() {
        try {
            String s = userController.removeFromBasket("name1", "1", null);
        } catch (NullPointerException ignored) {

        }
        List<Basket> expected = new ArrayList<>();
        Basket basket2 = new Basket();
        basket2.setProduct(2L);
        basket2.setUser(1L);
        Basket basket3 = new Basket();
        basket3.setProduct(2L);
        basket3.setUser(2L);
        expected.add(basket2);
        expected.add(basket3);
        assertEquals(expected.toString(), basketServiceMock.findAll().toString());
    }

    @Test
    public void loginTest() {
        Model model = new ExtendedModelMap();
        userController.login(model, null, null);
        assertEquals(new ExtendedModelMap(), model);
    }

    @Test
    public void loginTestWithError() {
        Model model = new ExtendedModelMap();
        userController.login(model, "", null);
        assertEquals("{error=Username or password is incorrect.}", model.toString());
    }

    @Test
    public void loginTestWithLogout() {
        Model model = new ExtendedModelMap();
        userController.login(model, null, "");
        assertEquals("{message=Logged out successfully.}", model.toString());
    }

    @Test
    public void loginTestWithErrorAndLogout() {
        Model model = new ExtendedModelMap();
        userController.login(model, "", "");
        assertEquals("{error=Username or password is incorrect., message=Logged out successfully.}", model.toString());
    }
}