package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.saveUser("Bob", "First", (byte) 99);
        userService.saveUser("Mary", "Second", (byte) 18);
        userService.saveUser("Jimmy", "Third", (byte) 25);
        userService.saveUser("Rob", "Fourth", (byte) 8);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
