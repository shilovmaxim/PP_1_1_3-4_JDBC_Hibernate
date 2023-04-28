package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users " +
                            "(id INT PRIMARY KEY AUTO_INCREMENT," +
                            " name VARCHAR(50)," +
                            " lastName VARCHAR(50)," +
                            " age INT(3))"
            );
            connectionCommit();
            LOGGER.log(Level.INFO, "Successful created table \"users\".");
        } catch (SQLException e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Failed to create a table.", e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "DROP TABLE IF EXISTS users"
            );
            connectionCommit();
            LOGGER.log(Level.INFO, "Successful dropped table \"users\".");
        } catch (SQLException e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Failed to drop a table.", e);

        }
        connectionCommit();

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepStatement = connection.prepareStatement(
                "INSERT INTO users (name, lastName, age) Values (?, ?, ?)")) {
            prepStatement.setString(1, name);
            prepStatement.setString(2, lastName);
            prepStatement.setByte(3, age);
            prepStatement.executeUpdate();
            connectionCommit();
            LOGGER.log(Level.INFO, "The following data has been successfully saved to the \"users\" table: "
                    + name + " " + lastName + " " + age);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Data saving error.", e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prepStatement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            prepStatement.setLong(1, id);
            prepStatement.executeUpdate();
            connectionCommit();
            LOGGER.log(Level.INFO, "Request completed successfully. User from id = " + id + " removed.");
        } catch (SQLException e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Request execution error.", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);
            }
            connectionCommit();
            LOGGER.log(Level.INFO, "Request completed successfully. The list of users has been received");
        } catch (SQLException e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Request execution error.", e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "TRUNCATE TABLE users"
            );
            connectionCommit();
            LOGGER.log(Level.INFO, "Successful truncated table \"users\".");
        } catch (SQLException e) {
            connectionRollback();
            LOGGER.log(Level.SEVERE, "Failed to truncated a table.", e);
        }
    }

    @Override
    public void connectionClose() {
        try {
            connection.close();
            LOGGER.log(Level.INFO, "Database connection successfully closed.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB error.", e);
        }
    }

    public void connectionCommit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB connection error.", e);
        }
    }

    public void connectionRollback() {
        try {
            connection.rollback();
            LOGGER.log(Level.SEVERE, "DB error.");
        } catch (SQLException e1) {
            LOGGER.log(Level.SEVERE, "DB connection error.", e1);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e2) {
                LOGGER.log(Level.SEVERE, "DB connection error.", e2);
            }
        }
    }
}
