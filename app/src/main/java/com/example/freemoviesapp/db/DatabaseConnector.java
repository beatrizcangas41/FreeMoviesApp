package com.example.freemoviesapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String url = "jdbc:mysql://localhost:3306/moviesapp";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() {

//        Properties info = new Properties();
//        info.put("user", user);
//        info.put("password", password);
//        dbConnection = DriverManager.getConnection(url, info);

        // Class.forName("com.mysql.jdbc.Driver");
        // Connection conn = DriverManager.getConnection(url, user, password);
        // Connection conn = DriverManager.getConnection(url + "user=" + user + "&password=" + password);

        Connection dbConnection;


        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, user, password);
            if (dbConnection != null) System.out.println("Successfully connected to MySQL database test");
            return dbConnection;
        }

        catch (SQLException | ClassNotFoundException ex) {
            System.out.println("An error occurred while connecting MySQL database");
            ex.printStackTrace();
            return null;
        }

//        if (conn != null) System.out.println("Successfully connected to MySQL database");
//        else System.out.println("Connection Error");
//        return conn;
//    }
//
//        catch (Exception e) {
//        System.out.println(e.getMessage());
//        return null;
//    }
    }
}
