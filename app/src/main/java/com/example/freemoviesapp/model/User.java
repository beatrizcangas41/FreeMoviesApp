package com.example.freemoviesapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String user_type;
    private Date accountOpen;

    public User(String firstName, String lastName, String username, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public User(String firstName, String lastName, String username, String email, Date openDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy:HH:mm:ss");
        this.accountOpen = openDate;
    }

    public User(int userID, String firstName, String lastName, String username, String password, String email, String user_type) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.user_type = user_type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getAccountOpen() {
        return accountOpen;
    }

    public void setAccountOpen(Date accountOpen) {
        this.accountOpen = accountOpen;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ",lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", user_type='" + user_type + '\'' +
                '}';
    }
}
