package org.yearup.data.mysql;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();
            // since the profile is being created nothing is being returned so return null
            return null;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile getByUserId(Profile profile, int userId)
    {
        //creating an instance of a profile to use in the sql query
        Profile profile1 = new Profile();
        String sql = """
                Select
                    user_id,
                    first_name,
                    last_name,
                    phone,
                    email,
                    address,
                    city,
                    state,
                    zip
                From
                    Profiles
                Where
                    user_id = ?
                
                """

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, userId);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                profile.setUserId(userId);
                profile.setFirstName(resultSet.getString("first_name"));
                profile.setLastName(resultSet.getString("last_name"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setEmail(resultSet.getString("email"));
                profile.setAddress(resultSet.getString("address"));
                profile.setCity(resultSet.getString("city"));
                profile.setState(resultSet.getString("state"));
                profile.setZip(resultSet.getString("zip"));
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
            return profile1;
    }


}
