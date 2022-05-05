package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        User user = new User();
        Connection connection = Util.connection;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE user (" +
                    "id long, name varchar(20), " + "lastName varchar(30)," + "age tinyint)");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        User user = new User();
        Connection connection = Util.connection;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE user");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        Connection connection = Util.connection;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO user VALUES (1, ?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        User user = null;
        Connection connection = Util.connection;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM user WHERE  id=?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List <User> person = new ArrayList<>();
        Connection connection = Util.connection;
        try {
            String SQL = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                person.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void cleanUsersTable() {
        User user = new User();
        Connection connection = Util.connection;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE user");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
