package com.distribuidas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl"; // Adjust SID if needed (xe, orcl, etc.)
    private static final String USER = "jrevelo";
    private static final String PASSWORD = "jrevelo";

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
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
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
