package com.oopfinal.game.crud;

import java.sql.*;

public class MySQLConnector {
    //access xampp using my account
    private static final String URL = "";
    private static final String USERNAME = "anais";
    private static final String PASSWORD = "092223_aa";

    public static Connection getConnection(){
        Connection c = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (ClassNotFoundException|SQLException e){
            //throw new RuntimeException();
            e.printStackTrace();
        }
        return c;
    }

    public static void main(String[] args) {
        MySQLConnector.getConnection();
    }
}
