package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private final static String URL = "jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false";
    private final static String USER = "root";
    private final static String PASSWORD = "used!Agency5Total7";

    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static int attempt = 0;

    /**
     * Делает до трех попыток подключения к БД.
     * @attempt: Количество попыток подключения к базе
     */
    public static Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.log(Level.INFO, "Successful connection to the database \"store\"");
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
        }
        attempt++;
        return attempt > 3 ? null : getConnection();
    }

}