package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        final UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Bob", "First", (byte) 99);
        userService.saveUser("Mary", "Second", (byte) 18);
        userService.saveUser("Jimmy", "Third", (byte) 25);
        userService.saveUser("Rob", "Fourth", (byte) 8);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        userService.connectionClose(); // Закрыть соединение

    }
}
