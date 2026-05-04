package com.islinkton.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconfig {

    public static Connection getDbConnection() {

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/islinkton",
                "root",
                ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}