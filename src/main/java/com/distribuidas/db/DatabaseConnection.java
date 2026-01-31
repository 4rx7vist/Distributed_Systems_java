package com.distribuidas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // Adjust SID if needed (xe, orcl, etc.)
    private static final String USER = "master";
    private static final String PASSWORD = "master";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load driver explicitly just in case, though usually not needed in modern JDBC
                Class.forName("oracle.jdbc.OracleDriver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection to Oracle Database established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("Oracle JDBC Driver not found.");
                e.printStackTrace();
                throw new SQLException("Oracle JDBC Driver not found.", e);
            }
        }
        return connection;
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
