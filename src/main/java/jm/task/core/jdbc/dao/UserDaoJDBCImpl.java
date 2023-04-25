package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection conn;
        try (Statement statement = Objects.requireNonNull(conn = Util.getConnection()).createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users " +
                            "(Id INT PRIMARY KEY AUTO_INCREMENT," +
                            " name VARCHAR(50)," +
                            " lastName VARCHAR(50)," +
                            " age INT(3))"
            );
            conn.commit();
            LOGGER.log(Level.INFO, "Successful created table \"users\".");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to create a table.", e);
        }
    }

    public void dropUsersTable() {
        Connection conn;
        try (Statement statement = Objects.requireNonNull(conn = Util.getConnection()).createStatement()) {
            statement.executeUpdate(
                    "DROP TABLE users"
            );
            conn.commit();
            LOGGER.log(Level.INFO, "Successful dropped table \"users\".");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to drop a table.", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection conn;
        try (Statement statement = Objects.requireNonNull(conn = Util.getConnection()).createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO users (name, lastName, age) Values ('" + name + "', '" + lastName + "', " + age + ")");
            conn.commit();
            LOGGER.log(Level.INFO, "The following data has been successfully saved to the \"users\" table: "
                    + name + " " + lastName + " " + age);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Data saving error.", e);
        }
    }

    public void removeUserById(long id) {
        Connection conn;
        try (Statement statement = Objects.requireNonNull(conn = Util.getConnection()).createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE Id=" + id);
            conn.commit();
            LOGGER.log(Level.INFO, "Request completed successfully. User from id = " + id + " removed.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Request execution error.", e);
        }
    }

    public List<User> getAllUsers() {
        Connection conn;
        List<User> usersList = new ArrayList<>();
        try (Statement statement = Objects.requireNonNull(conn =Util.getConnection()).createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("Id"));
                usersList.add(user);
            }
            conn.commit();
            LOGGER.log(Level.INFO, "Request completed successfully. The list of users has been received");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Request execution error.", e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        Connection conn;
        try (Statement statement = Objects.requireNonNull(conn = Util.getConnection()).createStatement()) {
            statement.executeUpdate(
                    "TRUNCATE TABLE users "
            );
            conn.commit();
            LOGGER.log(Level.INFO, "Successful truncated table \"users\".");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to truncated a table.", e);
        }
    }
}
