package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    private final Connection connection = Util.getConnection();

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                    "  `Name` VARCHAR(45) NOT NULL," +
                    "  `LastName` VARCHAR(45) NOT NULL," +
                    "  `Age` TINYINT NOT NULL," +
                    "  PRIMARY KEY (`id`))");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS user");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES ( ?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            System.out.println("User с именем – " + name + " добавлен в базу данных");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM user WHERE  id=?")) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> person = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet =
                statement.executeQuery("SELECT * FROM user")) {
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
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("TRUNCATE TABLE user");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
