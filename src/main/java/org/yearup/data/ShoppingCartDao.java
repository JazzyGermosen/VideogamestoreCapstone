package org.yearup.data;

import org.yearup.controllers.ShoppingCartController;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    ShoppingCart getByOrderId(int orderId);
    void addItemShoppingCart(int userId, ShoppingCartItem item);
    void updateItemShoppingCart(int userId, ShoppingCartItem item);
    void deleteShoppingCart(int userId);
    BigDecimal getShoppingCartTotal(ShoppingCart shoppingCart);

}
