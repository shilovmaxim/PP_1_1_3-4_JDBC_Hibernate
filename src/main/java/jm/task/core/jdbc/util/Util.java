package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private final static String URL = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
    private final static String USER = "root";
    private final static String PASSWORD = "used!Agency5Total7";
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Database connection error", e);
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);
            LOGGER.log(Level.INFO, "Successful connection to the database \"store\"");
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
        return getConnection();
    }

}