package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.data.OrderDao;
import org.yearup.data.ProductDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

import java.math.BigDecimal;

// spring will be able to read this class
@Service
public class CheckOutService {
    private ShoppingCartDao shoppingCartDao;
    private OrderDao orderDao;
    private ProductDao productDao;
    private ProfileDao profileDao;

    @Autowired
    public CheckOutService(ShoppingCartDao shoppingCartDao, OrderDao orderDao, ProductDao productDao, ProfileDao profileDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.profileDao = profileDao;
    }

    @Transactional
    // tells the method to make sure that if a transaction error occurs to back track the order
    public BigDecimal checkOut(int userId){
        ShoppingCart cart = shoppingCartDao.getByUserId(userId);
        Profile profile = profileDao.getByUserId(userId);

        int orderId = orderDao.createOrder(profile, cart);
        // cycling through the cart items
        cart.getItems().values().forEach(item -> {
            orderDao.updateStock(item.getProductId(), item.getQuantity());
            orderDao.orderLineItem(orderId, item);
        });

        shoppingCartDao.deleteShoppingCart(userId);
        return cart.getTotal();
    }

}
