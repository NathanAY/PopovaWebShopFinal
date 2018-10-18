package net.proselyte.springsecurityapp.controller;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import net.proselyte.springsecurityapp.model.Basket;
import net.proselyte.springsecurityapp.model.Product;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.UserServiceMock;
import net.proselyte.springsecurityapp.service.basket.BasketServiceMock;
import net.proselyte.springsecurityapp.service.order.OrderServiceMock;
import net.proselyte.springsecurityapp.service.product.ProductServiceMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ui.ModelMap;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(DataProviderRunner.class)
public class UserControllerTest {

    private UserController userController = new UserController();
    private final BasketServiceMock basketServiceMock = new BasketServiceMock();
    private final OrderServiceMock orderServiceMock = new OrderServiceMock();

    @Before
    public void setUp() throws Exception {
        ProductServiceMock productServiceMock = new ProductServiceMock();
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setCategoryName("fridges");
        product.setId(1L);
        productList.add(product);
        Product product1 = new Product();
        product1.setCategoryName("category2");
        product1.setId(2L);
        productList.add(product1);
        Product product2 = new Product();
        product2.setCategoryName("fridges");
        product2.setId(3L);
        productList.add(product2);
        productServiceMock.setProducts(productList);

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

        userController.setService(productServiceMock, "product");
        userController.setService(userServiceMock, "user");
        userController.setService(basketServiceMock, "basket");
        userController.setService(productServiceMock, "product");
        userController.setService(orderServiceMock, "order");
    }

    @After
    public void tearDown() throws Exception {
    }

    @DataProvider
    public static Object[][] categoryDataProvider() {
        Object[][] data = new Object[][] {
                {-1, "H"},
                {0,"D"}
        };
        return data;
    }

    @Test
    @AssertTrue
    @UseDataProvider("categoryDataProvider")
    public void productListCategory(int i, String s) {
        ModelMap modelMap = new ModelMap();
        String category = "fridges";
        try {
            userController.productList(modelMap, category);
        } catch (NullPointerException ignored) {

        }
        assertEquals(2, ((List<Product>) modelMap.get("list")).size());
    }

    @Test
    @AssertTrue
    public void productListAllCategory() {
        ModelMap modelMap = new ModelMap();
        String category = "All";
        try {
            userController.productList(modelMap, category);
        } catch (NullPointerException ignored) {

        }
        assertEquals(3, ((List<Product>) modelMap.get("list")).size());
    }

    @Test
    @AssertTrue
    public void productListCategoryNull() {
        ModelMap modelMap = new ModelMap();
        try {
            userController.productList(modelMap, null);
        } catch (NullPointerException ignored) {

        }
        assertEquals(3, ((List<Product>) modelMap.get("list")).size());
    }

    @Test
    public void createOrder() {
        try {
            userController.createOrder("name1", null);
        } catch (NullPointerException ignored) {

        }
        assertEquals(2, orderServiceMock.findAll().size());
        assertEquals(new Long(1), orderServiceMock.findAll().get(0).getProductId());
        assertEquals(new Long(1), orderServiceMock.findAll().get(0).getUserId());
        assertEquals(new Long(1), orderServiceMock.findAll().get(0).getQuantity());
    }
}