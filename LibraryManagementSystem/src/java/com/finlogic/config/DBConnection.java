package com.finlogic.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides database connections.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    static {
        try {
            // Loading the driver explicitly for older environments
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("[DBConnection] MySQL JDBC driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}