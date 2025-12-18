package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        // get all categories
        //creating a empty list to store objects in it
        List<Category> categories = new ArrayList<>();

        String sql = """
                
                Select
                    category_id,
                    name,
                    description
                From
                    categories;
                
                """;

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));

                categories.add(category);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        // get category by id
        // storing the sql query in a variable
        String sql = """
                Select
                    category_id,
                    name,
                    description
                From
                    categories
                Where
                    category_id = ?;
                
                """;
        // doing try catch block with connection, prepared statement and result set
        // to deal with all the info connection with my SQL
        try (Connection conn = getConnection();
        ){
             PreparedStatement statement = conn.prepareStatement(sql);
             statement.setInt(1,categoryId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return mapRow(resultSet);
            }

        }
        catch(SQLException e) {
        throw new RuntimeException(e);
    }
        return null;
    }

    @Override
    public Category create(Category category) {
        // create a new category

        String sql = """
                Insert Into
                    categories(name, description)
                Values
                    (?, ?);
                """;

        try(Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0){
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()){
                    int catId = generatedKeys.getInt(1);
                    return getById(catId);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void update(int categoryId, Category category) {
        // update category
        String sql = """
                Update
                    categories
                Set
                    name = ?,
                    description = ?
                Where
                    category_id = ?;
                """;

        try(Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, categoryId);

            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int categoryId) {
        // delete category
        String sql = """
                Delete From
                    categories
                Where
                    category_id = ?;
                
                """;

        try(Connection conn = getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
