package org.yearup.data.mysql;

import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class MySQLShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySQLShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        return null;
    }


    @Override
    public void addItemShoppingCart(int userId, ShoppingCartItem item) {

    }

    @Override
    public void updateItemShoppingCart(int userId, int item) {

    }

    @Override
    public void deleteShoppingCart(int userId) {

    }

    @Override
    public BigDecimal getShoppingCartTotal(ShoppingCart shoppingCart) {
        return null;
    }
}
