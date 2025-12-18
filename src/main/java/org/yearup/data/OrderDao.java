package org.yearup.data;

import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface OrderDao {

    public int createOrder(Profile profile, ShoppingCart cart);

    void orderLineItem(int orderId, ShoppingCartItem item);

    // updating the stock to let the database know that it has one less stock item
    void updateStock(int productId, int quantity);

}
