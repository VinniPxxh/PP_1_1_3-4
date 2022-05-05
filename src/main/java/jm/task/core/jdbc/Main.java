package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Vasiliy", "Pupkin", (byte) 30);

        userService.saveUser("Petr", "Smorkalin", (byte) 45);

        userService.saveUser("Vanya", "Sidorov", (byte) 21);

        userService.saveUser("Irina", "Kayratova", (byte) 23);

    }
}
