package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.crud.MySQLConnector;

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
    private Texture backgroundTexture;

    public HistoryScreen(final OOPFinal game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("img/history-bg.png"));

        // Add a back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.top().left().pad(10);
        backButtonTable.add(backButton).size(100, 50);
        stage.addActor(backButtonTable);

        refreshHistory();
    }

    void refreshHistory() {
        if (table != null) {
            table.remove();
        }

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(60); // Center the table and add some padding at the top

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);

        String query = "SELECT * FROM game";

        try (Connection c = MySQLConnector.getConnection();
             PreparedStatement pst = c.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            // Add column headers
            addTableHeaders();

            while (rs.next()) {
                int gameID = rs.getInt("gameID");
                String p1Name = rs.getString("player1name");
                int p1Score = rs.getInt("player1score");
                String p2Name = rs.getString("player2name");
                int p2Score = rs.getInt("player2score");

                addHistoryEntry(p1Name, p1Score, p2Name, p2Score, gameID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTableHeaders() {
        table.add(new Label("Player 1", skin)).pad(10).center();
        table.add(new Label("Score", skin)).pad(10).center();
        table.add(new Label("", skin)).pad(10).center(); // Empty cell for "VS"
        table.add(new Label("Player 2", skin)).pad(10).center();
        table.add(new Label("Score", skin)).pad(10).center();
        table.add(new Label("", skin)).pad(10).center(); // Empty cell for "Delete" button
        table.row();
    }

    private void addHistoryEntry(String p1Name, int p1Score, String p2Name, int p2Score, int gameID) {
        Label player1Label = new Label(p1Name, skin);
        Label player1ScoreLabel = new Label(String.valueOf(p1Score), skin);
        Label vsLabel = new Label(" VS ", skin);
        Label player2Label = new Label(p2Name, skin);
        Label player2ScoreLabel = new Label(String.valueOf(p2Score), skin);

        TextButton deleteButton = new TextButton("Delete", skin);
        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try (Connection c = MySQLConnector.getConnection();
                     PreparedStatement pst = c.prepareStatement("DELETE FROM game WHERE gameID= ?")) {
                    pst.setInt(1, gameID);
                    pst.executeUpdate();
                    refreshHistory();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        float buttonWidth = Gdx.graphics.getWidth() / 5f;
        float buttonHeight = Gdx.graphics.getHeight() / 20f;
        deleteButton.setSize(buttonWidth, buttonHeight);

        table.add(player1Label).pad(10).center();
        table.add(player1ScoreLabel).pad(10).center();
        table.add(vsLabel).pad(10).center();
        table.add(player2Label).pad(10).center();
        table.add(player2ScoreLabel).pad(10).center();
        table.add(deleteButton).pad(10).center();
        table.row();
        table.center();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

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
        backgroundTexture.dispose();
    }
}
