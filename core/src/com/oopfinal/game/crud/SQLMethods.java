package com.oopfinal.game.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLMethods {
    //cannot use maven here kay wala ta ga-javafx
    //will find solutions later
    //idk where to place ang mga crud, so temporary location sa ni sila

    //CREATION OF TABLE GAME, SCORES
    public static void createGame(){
        try(Connection c = MySQLConnector.getConnection()){
            if(c == null){
                System.out.println("Failed to connect");
                return;
            }

            c.setAutoCommit(false);

            try(Statement st = c.createStatement()){
                String createTblGame = "CREATE TABLE IF NOT EXISTS tblgame (" +
                        "gameID INT AUTO_INCREMENT PRIMARY KEY," +
                        "p1ID INT, FOREIGN KEY (p1ID) REFERENCES tblplayers(playerID)," +
                        "p1Username VARCHAR(50) NOT NULL," +
                        "p1Character VARCHAR(10) NOT NULL," +
                        "p1Score INT(999999) NOT NULL," +
                        "p2ID INT, FOREIGN KEY(p2ID) REFERENCES tblplayers(playerID)," +
                        "p2Username VARCHAR(50) NOT NULL," +
                        "p2Character VARCHAR(10) NOT NULL," +
                        "p2Score INT(999999) NOT NULL," +
                        "gameMapChosen VARCHAR(30) NOT NULL)," +
                        "gameIsDeleted BOOLEAN(1) DEFAULT 0";

                String createTblPlayers = "CREATE TABLE IF NOT EXISTS tblplayers(" +
                        "playerID INT AUTO_INCREMENT PRIMARY KEY," +
                        "playerUsername VARCHAR(50) NOT NULL)" +
                        "isDeleted BOOLEAN(1) DEFAULT 0";

                st.execute(createTblGame);
                st.execute(createTblPlayers);

                c.commit();
                System.out.println("Created tables");
            }catch (SQLException e){
                c.rollback();
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //INSERT = NEW GAME -> INSERT NEW GAME TO TABLE
    public static void insertGame(int gameID){
        try(Connection c = MySQLConnector.getConnection(); PreparedStatement pst = c.prepareStatement(
                "INSERT INTO tblgame(gameID, p1Username, p2Username, p1Character, p2Character, gameMapChosen) VALUES (?, ?, ?, ?, ?, ?)"
        )){
            //make new game
            pst.setInt(1,gameID);

            int rowsInserted = pst.executeUpdate();

            if(rowsInserted > 0){
                //prompt
                System.out.println("Game created successfully");
            }
        }catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }
    public static void insertPlayer(String player, int playerID){
        try(Connection c = MySQLConnector.getConnection(); PreparedStatement pst = c.prepareStatement(
                "INSERT INTO tblplayers(playerID, playerUsername) VALUES (?, ?)"
        )){
            pst.setInt(1, playerID);
            pst.setString(2, player);

            int rowsInserted = pst.executeUpdate();
            if(rowsInserted >0){
                System.out.println("Player added.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    //RETRIEVE = LOAD GAME -> RETRIEVE GAME FROM TABLE
    public static void loadGame(){
        try(Connection c = MySQLConnector.getConnection(); PreparedStatement pst = c.prepareStatement(
                "SELECT FROM tblgame(gameID, p1ID, p2ID) WHERE p1ID = tblplayers.playerID AND p2ID = tblplayers.playerID"
        )){

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //UPDATE = score
    public static void updateScore(String username1, String username2, int score, int score2){
        try(Connection c = MySQLConnector.getConnection();
            PreparedStatement pst = c.prepareStatement(
                    "UPDATE player SET p1Score = ?, p2Score = ? WHERE p1Username = ? AND p2Username = ?"
            )
        ){
            pst.setInt(1, score); //for player 1
            pst.setString(2, username1);
            pst.setInt(3, score2); //for player 2
            pst.setString(4, username2);
            int rowsUpdated = pst.executeUpdate();
            System.out.println("Scores updated for " + username1 + " and "+ username2 + " \nRows updated: " + rowsUpdated);

        }catch (SQLException e){
            throw new RuntimeException("Error updating score");
        }
    }


    //DELETE USER AND GAME
    public static void deleteGame(int gamedID){
        try(Connection c = MySQLConnector.getConnection(); PreparedStatement pst = c.prepareStatement(
                "UPDATE FROM tblgame(gameID) SET gameIsDeleted = 1 WHERE gameID = gamedID"
        )){
            pst.setInt(11, 1);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void deleteUser(int playerID){
        try(Connection c = MySQLConnector.getConnection(); PreparedStatement pst = c.prepareStatement(
                "UPDATE FROM tblplayers(isDeleted) SET isDeleted = 1 WHERE playerID = tblplayers.playerID"
        )){
            pst.setInt(3, 1);
            int rowsUpdated = pst.executeUpdate();
            System.out.println("Deactivated account for " + playerID + " Rows updated: " + rowsUpdated);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
