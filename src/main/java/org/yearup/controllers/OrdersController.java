package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@PreAuthorize("hasRole('USER', 'ADMIN')")
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController {
    // instatiating the users Shopping cart so that it can be used
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private OrderDao orderDao;



    // creating autowired constructor
    @Autowired
    public OrdersController(ShoppingCartDao shoppingCartDao, UserDao userDao, OrderDao orderDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.orderDao = orderDao;
    }



    @PostMapping("")
    public void checkOut(Principal principal, Profile profile){
        try
        {
            // getting the logged in info
            String userName = principal.getName();

            // getting the user
            User user = userDao.getByUserName(userName);

            int userId = user.getId();

            // getting the shopping cart
            ShoppingCart cart = ShoppingCartDao.getByUserId(userId);
            // creating a variable named save order to store the order created from order dao
            // im assuming
            OrderDao savedOrder = orderDao.createOrder(profile, cart);

            //creating an if statement for the order check out
            if (cart.getItems().isEmpty()){
                System.out.println("shopping cart is empty!");

            }
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
