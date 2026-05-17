package com.hashing.utils;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconfig {

    private static final String URL = "jdbc:mysql://localhost:3306/week5";
    private static final String USER = "root";
    private static final String PASSWORD = "";

 
    public static Connection getConnection() {

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("✅ Connected to DB");

        } catch (Exception e) {
            System.out.println("❌ DB Connection Failed");
            e.printStackTrace(); // 👉 THIS will show REAL problem
        }

        return conn;
    }
}
