package com.oopfinal.game.crud;

import java.sql.*;

public class MySQLConnector {
    //access xampp using my account

    private static final String URL = "jdbc:mysql://localhost:3306/oop2final";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("conntected");
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return c;
    }
    public static void main(String[] args) {
        getConnection();
    }



//    public static void main(String[] args) {
//        MySQLConnector.getConnection();
//    }
}
