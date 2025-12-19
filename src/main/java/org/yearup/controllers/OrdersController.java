package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import org.yearup.service.CheckOutService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@PreAuthorize("hasRole('USER', 'ADMIN')")
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController {
    // instatiating the users Shopping cart so that it can be used
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private OrderDao orderDao;
    private CheckOutService checkOutService;



    // creating autowired constructor
    @Autowired
    public OrdersController(ShoppingCartDao shoppingCartDao, UserDao userDao, OrderDao orderDao, CheckOutService checkOutService) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.checkOutService = checkOutService;
    }




    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Object> checkOut(Principal principal){
        try
        {
            // getting the logged in info
            String userName = principal.getName();

            // getting the user
            User user = userDao.getByUserName(userName);
            System.out.println("hiiii im daisy");
            BigDecimal total = checkOutService.checkOut(user.getId());
            System.out.println("hiiiii im not daisy");
            // same as list but is pared with a key
            Map<String, Object> outPut = new HashMap<>();

            outPut.put("total", total);
            //outPut.put("username", userName);


            return outPut;

        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
