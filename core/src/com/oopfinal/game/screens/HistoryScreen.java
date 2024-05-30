package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.crud.MySQLConnector;
import com.oopfinal.game.crud.SQLMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryScreen implements Screen {
    private final OOPFinal game;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private Table table;
    private String[] history;

    public HistoryScreen(final OOPFinal game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json")); // Assuming you have a skin file
        refreshHistory();


        //nico vs coni
    }
    void refreshHistory(){
        table = new Table();
        table.setFillParent(true);
//        ScrollPane scrollPane
        stage.addActor(table);
        String query = "SELECT * FROM game ";

        try(Connection c = MySQLConnector.getConnection();
            PreparedStatement pst = c.prepareStatement(query)){
//                pst.setInt(1, gameID);
            try(ResultSet rs = pst.executeQuery()){
                while(rs.next()){
                    int gameID = rs.getInt("gameID");
                    String p1Name = rs.getString("player1name");
                    int p1Score=rs.getInt("player1score");
                    String p2Name = rs.getString("player2name");
                    int p2Score=rs.getInt("player2score");

                    System.out.println("Game found!\nGame ID: " + gameID);
                    System.out.println("Player 1: " + p1Name);
                    System.out.println("Player 2: " + p2Name);
                    addHistoryEntry(p1Score+" "+p1Name+" Vs "+ p2Name+" "+p2Score,gameID);


                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private void addHistoryEntry(String entry,int id) {
        Label historyLabel = new Label(entry, skin);
        TextButton deleteButton = new TextButton("Delete", skin);
        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try(Connection c = MySQLConnector.getConnection();
                    PreparedStatement pst = c.prepareStatement("DELETE FROM game where gameID= "+id+"")){
                    pst.executeUpdate();
                    stage.clear();

                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
                refreshHistory();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return true;
            }
        });
        // Set button sizes to be proportional to screen size
        float buttonWidth = Gdx.graphics.getWidth() / 5f;
        float buttonHeight = Gdx.graphics.getHeight() / 20f;
        deleteButton.setSize(buttonWidth, buttonHeight);

        // Set label width to adapt to screen width
        historyLabel.setWrap(true);
        historyLabel.setWidth(Gdx.graphics.getWidth() / 2f);

        table.add(historyLabel).width(Gdx.graphics.getWidth() * 0.6f).pad(5);
        table.add(deleteButton).pad(5).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}