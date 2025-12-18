package org.yearup.data.mysql;

import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

public class MySQLShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySQLShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql =


        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();

            //setting values to be entered into the (?????)




        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }

        return null;
    }


    @Override
    public void addItemShoppingCart(int userId, ShoppingCartItem item) {
        String sql =
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();

            //setting values to be entered into the (?????)



        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItemShoppingCart(int userId, int item) {
        String sql =
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();

            //setting values to be entered into the (?????)



        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteShoppingCart(int userId) {
        String sql =


        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();




        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getShoppingCartTotal(ShoppingCart shoppingCart) {
        String sql =


        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();




        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }
        return null;
    }
}
