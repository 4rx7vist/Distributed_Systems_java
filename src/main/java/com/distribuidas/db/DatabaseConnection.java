package com.distribuidas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // Adjust SID if needed (xe, orcl, etc.)
    private static String user = "master";
    private static String password = "master";

    public static void setCredentials(String newUser, String newPassword) {
        user = newUser;
        password = newPassword;
        // Close existing connection to force reconnection with new credentials
        closeConnection();
    }

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = getDedicatedConnection();
        }
        return connection;
    }

    public static Connection getDedicatedConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(URL, user, password);
            System.out.println("New dedicated connection to Oracle Database established.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found.");
            throw new SQLException("Oracle JDBC Driver not found.", e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
