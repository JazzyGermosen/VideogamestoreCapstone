package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

@Component
public class MyOrderSqlDao extends MySqlDaoBase implements OrderDao {
    // have generated constructor
    public MyOrderSqlDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int createOrder(Profile profile, ShoppingCart cart) {
        // get total price of shopping cart items for our order to check out
        BigDecimal total = cart.getTotal();
        // getting the local date time
        LocalDateTime currentDate = LocalDateTime.now();
        // setting sql  query in a variable to be used in prepared statement later

        int orderId = 0;

        String sql = """
                Insert Into
                    Orders(user_id, date, address, city, state, zip, shipping_amount)
                Values
                    (?,?,?,?,?,?,?)
                
                """;
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            //setting values to be entered into the (?????)
            preparedStatement.setInt(1, profile.getUserId());

            preparedStatement.setString(2, "now()");

            preparedStatement.setString(3, profile.getAddress());

            preparedStatement.setString(4, profile.getCity());

            preparedStatement.setString(5, profile.getState());

            preparedStatement.setString(6, profile.getZip());

            preparedStatement.setBigDecimal(7, total);

           preparedStatement.executeUpdate();


           try(
                   ResultSet resultSet = preparedStatement.getGeneratedKeys()
                   ){
               if (!resultSet.next()){
                   throw new SQLException("order create failed");
               }
               orderId = resultSet.getInt(1);
           }


        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }
        return orderId;


    }

    @Override
    public void orderLineItem(int orderId, ShoppingCartItem item) {
        String sql = """
                Insert Into
                    order_line_items(order_id, product_id, sales_price, quantity, discount)
                Values
                    (?,?,?,?,?)
                
                """;

        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            //setting values to be entered into the (??????)
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, item.getProductId());
            preparedStatement.setBigDecimal(3, item.getLineTotal());
            preparedStatement.setInt(4, item.getQuantity());
            preparedStatement.setBigDecimal(5, item.getDiscountPercent());
            ResultSet resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println("An error occured when trying to add the order to the database");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateStock(int productId, int quantity) {

        String sql = """
                Update
                    products
                Set
                    stock = stock - ?
                Where
                    producid = ?
                    And stock >= ?
                """;

        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            //setting values to be entered into the (??????)
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0){
               throw new RuntimeException("Oopsie, we don't have that");
            }

        } catch (SQLException e) {
            System.out.println("An error occured when trying to add the order to the database");
            throw new RuntimeException(e);
        }
    }
}