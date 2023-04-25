package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
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
