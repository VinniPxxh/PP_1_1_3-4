package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/pp_base" + "useSSL=false" + "&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!Vinni6325";

    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}