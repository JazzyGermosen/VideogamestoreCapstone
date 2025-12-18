package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    void addItemShoppingCart(int userId, int productId);
    void updateItemShoppingCart(int userId, int item, int quantity);
    void deleteShoppingCart(int userId);


}
