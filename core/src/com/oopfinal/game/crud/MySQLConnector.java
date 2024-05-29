package com.oopfinal.game.crud;

import java.sql.*;

public class MySQLConnector {
    //access xampp using my account
    private static final String URL = "jdbc:mysql://localhost:3306/dbtaniya";

    //naka-declare nas xampp
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
//        Connection c = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try(Connection c = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
                if(c != null)
                    System.out.println("Connected to database succesfully");
                else
                    System.out.println("Failed to connect");

                return c;
            }
//            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Failed to connect");
            throw new RuntimeException();
        }
    }

    public static void main(){
        getConnection();
        SQLMethods.createGame();
    }
} //end of class


