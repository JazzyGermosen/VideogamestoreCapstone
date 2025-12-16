package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController

public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;



    // each method in this controller requires a Principal object as a parameter
    /* this didnt have an pre authorize but if a user is getting the cart
    doesnt that mean they need to have an account to access the cart??
    * */
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return null;
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    // adding a post method
    @PostMapping("{productId}")
    // making sure that only people who are users have access to this
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart addProduct(@RequestBody Principal principal){
        try{
            return
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }




    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart

}
