package com.javapython.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTester {

    public static void main(String[] args){
        // Oracle database connection details
        String url = "jdbc:oracle:thin:@//oracle9:1521/hr";
        String user = "hr"; // Replace with your username
        String password = "N1ght_G00d"; // Replace with your password

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            System.out.println("Connected to the Oracle database!");

            // Example query
            String query = "SELECT * FROM employees";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
