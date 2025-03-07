package com.example.roomallocationsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sql {
    private static final String DATABASE_NAME = "project";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "@Najma321";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;

    public static Connection getConnection() {
        Connection databaseLink = null;
        try {
            // Load MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Attempt to connect to the database
            databaseLink = DriverManager.getConnection(URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Database Connected Successfully!");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found! Please check the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database Connection Failed! Please check your credentials or database status.");
            e.printStackTrace();
        }
        return databaseLink;
    }

    // Method to close the connection safely
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection.");
            e.printStackTrace();
        }
    }
}
