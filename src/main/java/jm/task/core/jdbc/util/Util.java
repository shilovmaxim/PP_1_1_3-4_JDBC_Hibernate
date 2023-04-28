package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Util {
    private final static String DB_URL = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "used!Agency5Total7";
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static final SessionFactory sessionFactory = configSessionFactory();


    private static SessionFactory configSessionFactory() {
        final StandardServiceRegistry registry;
        final Configuration configuration = new Configuration();
        try {
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, DB_URL);
            properties.put(Environment.USER, DB_USER);
            properties.put(Environment.PASS, DB_PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "error config SessionFactory", e);
        }
        registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        try {
            return configuration.buildSessionFactory(registry); // sessionFactory
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "error creating SessionFactory", e);
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            configSessionFactory();
        }
        return sessionFactory;
    }

    public static void close() {
        getSessionFactory().close();
    }

    public static Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
            LOGGER.log(Level.INFO, "Successful connection to the database \"store\"");
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
        return getConnection();
    }

}