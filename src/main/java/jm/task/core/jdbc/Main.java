package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService() {
            @Override
            public void createUsersTable() {
            }

            @Override
            public void dropUsersTable() {

            }

            @Override
            public void saveUser(String name, String lastName, byte age) {
                name = "Igor";
                lastName = "Kuznetsov";
                age = 20;
            }

            @Override
            public void removeUserById(long id) {
                id = 1;
            }

            @Override
            public List<User> getAllUsers() {
                return null;
            }

            @Override
            public void cleanUsersTable() {

            }
        };
    }
}
