package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.*;
import net.proselyte.springsecurityapp.service.SecurityService;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.service.basket.BasketService;
import net.proselyte.springsecurityapp.service.order.OrderService;
import net.proselyte.springsecurityapp.service.product.ProductService;
import net.proselyte.springsecurityapp.service.product.category.CategoryService;
import net.proselyte.springsecurityapp.validator.ProductValidator;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ProductValidator productValidator;
    @Autowired
    private ProductService productService;
    @Autowired
    private BasketService basketService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CategoryService categoryService;

    public void setService(Object service, String type) {
        switch (type) {
            case "product":
                this.productService = (ProductService) service;
                break;
            case "basket":
                this.basketService = (BasketService) service;
                break;
            case "user":
                this.userService = (UserService) service;
                break;
            case "order":
                this.orderService = (OrderService) service;
                break;
        }
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String myAccount(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User user = userService.findByUsername(curUserName);
        user.setPassword("");
        model.addAttribute("userForm", user);
        return "user/myAccount";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String accountChange(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User user = userService.findByUsername(curUserName);
        userForm.setId(user.getId());

        userValidator.validateUpdate(userForm, bindingResult);
        if (userForm.getPassword() != null && userForm.getPassword() != "") {
            userValidator.validatePassword(userForm, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            return "user/myAccount";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
//        EmailSender emailSender = new EmailSender();
//        emailSender.sendEmail(userForm.getUsermail());
//        return "redirect:/verifyAccount";
        return "redirect:/productList";
    }

        @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String simple(ModelMap map) {
        map.addAttribute("list", userService.findAll());
        return "user/userList";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("name") String name, @RequestParam("userId") String userId, ModelMap map) {
        Long id = Long.parseLong(userId);
        List<User> users = userService.findAll();
        for (User user: users) {
            if (user.getId() == id) {
                user.setPassword("inactive");
                user.setConfirmPassword("inactive");
                userService.save(user);
            }
        }
        return null;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProduct(ModelMap map) {
        map.addAttribute("list", userService.findAll());
        map.addAttribute("productForm", new Product());
        return "product/addProduct";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String product(@PathVariable("id") Long id, ModelMap map) {
        List<Product> products = new ArrayList<>();
        if (productService.findById(id) == null) {
            return null;
        }
        products.add(productService.findById(id));
        map.addAttribute("list", products);
        map.addAttribute("productForm", new Product());
        return "product/product";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public String product(@PathVariable("id") Long id,
            @ModelAttribute("productForm") Product productForm,
                          BindingResult bindingResult, Model model) {
        productValidator.validate(productForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "product/product";
        }
        String productCategoryName = productForm.getCategoryName();
        Category category =  categoryService.findByName(productCategoryName);
        if (category == null) {
            category = new Category();
            category.setName(productCategoryName);
            categoryService.save(category);
        }
        productForm.setCategoryId(categoryService.findByName(productCategoryName).getId());
        productForm.setId(id);
        productService.save(productForm);
        return "redirect:/productList";
    }

    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    public String productList(ModelMap map,
    @RequestParam(value = "category", required = false) String category) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName(); //TODO everything
        if (category != null && !category.equals("All")) {
            List<Product> products = productService.findAll();
            products.removeIf(product -> !product.getCategoryName().equals(category));
            map.addAttribute("list", products);
        } else {
            map.addAttribute("list", productService.findAll());
        }
        map.addAttribute("categories", categoryService.findAll());
        return "product/productList";
    }

    @RequestMapping(value = "/search", params = {"search"}, method = RequestMethod.GET)
    public String search( @RequestParam("search") String search, ModelMap map) {
        map.addAttribute("list", productService.findAllByNameContaining(search));
        return "product/productList";
    }

    @RequestMapping(value = "/basket", params = "search", method = RequestMethod.GET)
    public String basket(@RequestParam("search") String search, ModelMap map) {
        Long userId = userService.findByUsername(search).getId();
        List<Basket> basketList = basketService.findAllByUser(userId);
        List<Product> productList = new ArrayList<>();
        for (Basket basket: basketList) {
            Product product = productService.findById(basket.getProduct());
            if (product != null) {
                productList.add(product);
            }
        }
        map.addAttribute("list", productList);
        return "product/basket";
    }

    @RequestMapping(value = "/addToBasket", method = RequestMethod.POST)
    public String addToBasket(@RequestParam("name") String name, @RequestParam("product") String product, ModelMap map) {
        Long userId = userService.findByUsername(name).getId();
        Long productId = Long.parseLong(product);
        Basket basket = new Basket();
        basket.setUser(userId);
        basket.setProduct(productId);
        basket.setQuantity(1L);
        basketService.save(basket);
        map.addAttribute("list", "OK");
        return "success";
    }

    @RequestMapping(value = "/removeFromBasket", method = RequestMethod.POST)
    public String removeFromBasket(@RequestParam("name") String name, @RequestParam("product") String product, ModelMap map) {
        Long userId = userService.findByUsername(name).getId();
        Long productId = Long.parseLong(product);
        basketService.deleteByProductAndUser(productId, userId);
        map.addAttribute("list", "OK");
        return "success";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(@RequestParam("name") String name, ModelMap map) {
        Long userId = userService.findByUsername(name).getId();
        List<Basket> basketList = basketService.findAllByUser(userId);
        for (Basket basket: basketList) {
            Long productid = basket.getProduct();
            Product product = productService.findById(productid);
            if (product == null) {
                basketService.deleteByProductAndUser(productid, userId);
                continue;
            }
            Order order = new Order();
            order.setProductId(productid);
            order.setProductName(product.getName());
            order.setUserId(userId);
            order.setUserName(name);
            order.setCost(product.getPrice());
            order.setQuantity(1L);
            orderService.save(order);
            basketService.deleteByProductAndUser(productid, userId);
        }
        map.addAttribute("list", "OK");
        return "success";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "product/orders";
    }

    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public String deleteOrder(@RequestParam("name") String name, @RequestParam("orderId") String orderId, ModelMap map) {
        orderService.deleteById(Long.parseLong(orderId));
        return null;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "product/category/categories";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    public String deleteCategory(@RequestParam("name") String name, @RequestParam("categoryId") String categoryId, ModelMap map) {
        Long id = Long.parseLong(categoryId);
        categoryService.deleteById(id);
        return null;
    }

    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    public String createCategory(@RequestParam("name") String name, ModelMap map) {
        Category category = new Category();
        category.setName(name);
        categoryService.save(category);
        return "success";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model) {
        productValidator.validate(productForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "product/addProduct";
        }
        String productCategoryName = productForm.getCategoryName();
        Category category =  categoryService.findByName(productCategoryName);
        if (category == null) {
            category = new Category();
            category.setName(productCategoryName);
            categoryService.save(category);
        }
        productForm.setCategoryId(categoryService.findByName(productCategoryName).getId());
        productService.save(productForm);
        return "redirect:/addProduct";
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("productId") String productId) {
        productService.deleteById(Long.parseLong(productId));
        return "ok";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
//        EmailSender emailSender = new EmailSender();
//        emailSender.sendEmail(userForm.getUsermail());
//        return "redirect:/verifyAccount";
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/verifyAccount"}, method = RequestMethod.GET)
    public String verifyAccount(Model model) {
//        return "welcome";
        return "redirect:/productList";
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
    public String deleteProduct(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/deleteProduct";
    }

    @RequestMapping(value = {"/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "redirect:/productList";
//        return "redirect:/welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
