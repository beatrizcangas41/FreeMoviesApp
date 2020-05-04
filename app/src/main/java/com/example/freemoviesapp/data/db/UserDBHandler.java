package com.example.freemoviesapp.data.db;

import com.example.freemoviesapp.data.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.freemoviesapp.data.util.JavaMail.getToken;

public class UserDBHandler {
    private static Connection connection = DatabaseConnector.getConnection();

    public static User getUserByUsername(String username) throws SQLException{
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        User user = null;
        while (results.next()){
            String firstName = results.getString("firstName");
            String lastName = results.getString("lastName");
            String email = results.getString("email");
            Date openDate = results.getDate("createdOn");
            user = new User(firstName, lastName, username, email, openDate);
        }

        return user;
    }

    public static int getUserIDByUsername(String username) throws SQLException {
        String query1 = "SELECT userID FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results1 = pstmt.executeQuery(query1);

        int userID = 0;

        while (results1.next()) userID = results1.getInt("userID");
        System.out.println("user_type: " + userID);

        return userID;
    }

    public static String getUserTypeFromUsername(String username) throws SQLException {
        String query1 = "SELECT user_type FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results1 = pstmt.executeQuery(query1);

        String usertype = null;

        while (results1.next()) {
            usertype = results1.getString("user_type");
        }

        System.out.println("user_type: " + usertype);

        if (usertype != null && usertype.equals("Admin")) System.out.println("Admin Page");
        else if (usertype != null && usertype.equals("User")) System.out.println("User Page");
        else System.out.println("User not classified");

        return usertype;
    }

    public static void addUser(String firstName, String lastName, String email, String username, String password) throws SQLException {

        Statement s = connection.createStatement();
        password = BCrypt.hashpw(password, BCrypt.gensalt());

        s.executeUpdate("INSERT INTO `user`(firstName, lastName, email, username, password)" +
                " VALUE ('" + firstName + "' , '" + lastName + "' , '" + email + "', '" + username + "', '" + password + "')");
    }

    public static void addUserAdmin(String name, String email, String username, String password, String user_type) throws SQLException {

        Statement s = connection.createStatement();
        password = BCrypt.hashpw(password, BCrypt.gensalt());

        s.executeUpdate("INSERT INTO `user`(name, email, username, password, user_type)" +
                " VALUE ('" + name + "' , '" + email + "', '" + username + "', '" + password + "', '"+ user_type +"')");
    }

    public static boolean addToken(String token, String username) throws SQLException {

        String query = "UPDATE user SET token = ? where username = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);

        pstmt.setString(1, token);
        pstmt.setString(2, username);

        return pstmt.execute();
    }

    public static boolean userExists (String username) throws SQLException {
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        String uName = null;

        while (results.next()) {
            uName = results.getString("username");

            System.out.println("username: " + username);
            System.out.println("dbUname: " + uName);
        }

        if (uName != null && username != null) {
            if (uName.equals(username)) {
                System.out.println("User exists");
                return true;
            } else {
                System.out.println("User does NOT exists");

                System.out.println("username: " + username);
                System.out.println("dbUname: " + uName);
                return false;
            }
        }

        else System.out.println("User does NOT exists");

        return username.equals(uName);
    }

    public static boolean verifyUsername(String username) {
        try {
            String query1 = "SELECT * FROM user WHERE username = '" + username + "'";
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet results = pstmt.executeQuery(query1);

            String dbUsername = null;
            while (results.next()) {
                dbUsername = results.getString("username");
                System.out.println("db pw: " + dbUsername);
                System.out.println("pw entered: " + username);
            }

            if (username != null && username.equals(dbUsername)) {
                System.out.println("It matches");
                return true;
            }

            else {
                System.out.println("It does not match");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean verifyPassword(String username, String password) throws SQLException {

        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        System.out.println("pw entered: " + password);

        String dbPassword = null;

        while (results.next()) {
            dbPassword = results.getString("password");
            System.out.println("db pw: " + dbPassword);
            System.out.println("pw entered: " + password);
        }

        if (password != null && dbPassword != null && BCrypt.checkpw(password, dbPassword)) {
            System.out.println("It matches");
            return true;
        }

        else {
            System.out.println("It does not match");
            System.out.println("db pw: " + dbPassword);
            System.out.println("pw entered: " + password);
            return false;
        }

    }

    public static boolean verifyEmail(String username, String email) throws SQLException {

        String query = "SELECT email FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet results = pstmt.executeQuery(query);

        String emailFromDatabase = "";
        while (results.next()){
            emailFromDatabase = results.getString("email");
        }

        return email.equals(emailFromDatabase);
    }

    public static boolean verifyToken (String token) throws SQLException {
        String query1 = "SELECT * FROM user WHERE token = '" + token + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results1 = pstmt.executeQuery(query1);

        String token1 = null;

        while (results1.first()) {
            token1 = results1.getString("token");
        }

        System.out.println("token: " + token);
        System.out.println("dbToken: " + token1);

        if (token1 != null && token1.equals(getToken())) {
            System.out.println("token is a match. ");
            return true;
        }
        else {
            System.out.println("wrong input");
            return false;
        }

    }

    public static ResultSet verifyUsernameAndEmail (String username, String email) throws SQLException {

        String query2 = "SELECT username AND email FROM user " +
                "WHERE username = '" + username + "' AND email = '" + email + "'";

        PreparedStatement pstmt = connection.prepareStatement(query2);

        return pstmt.executeQuery(query2);
    }

    // NOT UTILIZED AT THE MOMENT BECAUSE THERE IS NOTHING THAT INVOLVES SHIPPING

//    public static void updateAddress(String address, String username) throws SQLException {
//
//        String query = "UPDATE user SET address = ? where username = ?";
//        PreparedStatement pstmt1 = connection.prepareStatement(query);
//
//        pstmt1.setString(1, address);
//        pstmt1.setString(2, username);
//
//        pstmt1.execute();
//    }

//    public static String getAddressFromUsername(String username) throws SQLException {
//        String query1 = "SELECT address FROM user WHERE username = '" + username + "'";
//        PreparedStatement pstmt = connection.prepareStatement(query1);
//        ResultSet results1 = pstmt.executeQuery(query1);
//
//        String address = null;
//
//        while (results1.next()) {
//            address = results1.getString("address");
//        }
//
//        System.out.println("address: " + address);
//
//        return address;
//    }


//    public static User getUserByUsernameWithAddress(String username) throws SQLException{
//        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";
//
//        PreparedStatement pstmt = connection.prepareStatement(query1);
//        ResultSet results = pstmt.executeQuery(query1);
//
//        User user = null;
//        while (results.next()){
//            String firstName = results.getString("firstName");
//            String lastName = results.getString("lastName");
//            String email = results.getString("email");
//            String username1 = results.getString("username1");
//            user = new User(firstName, lastName, username, email);
//        }
//
//        return user;
//    }

}
