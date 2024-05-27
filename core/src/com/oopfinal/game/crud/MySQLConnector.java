package com.oopfinal.game.crud;

import java.sql.*;

public class MySQLConnector {
    //access xampp using my account
    private static final String URL = "jdbc:mysql://localhost:3306/dbtaniya";

    //naka-declare nas xampp
    private static final String USERNAME = "anais";
    private static final String PASSWORD = "092223_aa";

    public static Connection getConnection(){
        Connection c = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("Connected to database");
        }catch (ClassNotFoundException|SQLException e){
            //throw new RuntimeException();
            e.printStackTrace();
        }
        return c;
    }
}
