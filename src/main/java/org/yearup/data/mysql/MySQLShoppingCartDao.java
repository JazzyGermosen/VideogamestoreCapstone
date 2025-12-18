package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
@Component
@Repository
public class MySQLShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {


    public MySQLShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        // instatiating shopping cart
        ShoppingCart cart = new ShoppingCart();

        String sql = """
                Select
                    user_id,
                    S.product_id,
                    quantity,
                    P.*
                From
                    shopping_cart S
                Join
                    products P On (P.product_id = S.product_ID)    
                Where
                    user_id = ?
                """;


        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            //setting values to be entered into the (?????)

            while(resultSet.next()){
                Product product = new Product();
                int quantity = resultSet.getInt("quantity");
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setCategoryId(resultSet.getInt("category_id"));
                product.setDescription(resultSet.getString("description"));
                product.setSubCategory(resultSet.getString("subcategory"));
                product.setStock(resultSet.getInt("stock"));
                product.setFeatured(resultSet.getBoolean("featured"));
                product.setImageUrl(resultSet.getString("image_url"));

                ShoppingCartItem item = new ShoppingCartItem();
                // was not updating the item before adding it to the cart
                item.setProduct(product);
                item.setQuantity(quantity);
                cart.add(item);

            }
        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }

        return cart;
    }


    @Override
    public void addItemShoppingCart(int userId, int productId) {

        String sql = """
                Insert Into
                    shopping_cart(user_id, product_id, quantity)
                values
                    (?, ?, 1)
                ON DUPLICATE KEY UPDATE 
                    quantity = quantity + 1
                """;

        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            //setting values to be entered into the (?????)
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in the checkout phase");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateItemShoppingCart(int userId, int productId, int quantity) {

        String sql = """
                Update
                    shopping_cart
                Set
                    quantity = ?
                Where
                    user_id = ?
                    and product_id = ?
                    and quantity >= 1
                """;

        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            //setting values to be entered into the (?????)
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, productId);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error occured in when updating an item");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteShoppingCart(int userId) {
        String sql = """
                Delete From
                    shopping_cart
                Where
                    user_id = ?
                """;


        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // throw error if doesnt work
            System.out.println("An error when deleting item");
            throw new RuntimeException(e);
        }
    }


}
