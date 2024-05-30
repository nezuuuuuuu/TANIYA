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

public class HistoryScreen implements Screen {
    private final OOPFinal game;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;
    private Table table;
    private Table backButtonTable; // Table for the back button
    private String[] history;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public HistoryScreen(final OOPFinal game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json")); // Assuming you have a skin file

        backgroundTexture = new Texture(Gdx.files.internal("img/history-bg.png"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.center().bottom();
        stage.addActor(backButtonTable);

        history = new String[]{
                "Player 1: Alice vs Player 2: Bob",
                "Player 1: Carol vs Player 2: Dave",
                "Player 1: Eve vs Player 2: Frank"
        };

        for (String entry : history) {
            addHistoryEntry(entry);
        }

        // Add back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        backButtonTable.add(backButton).padBottom(100);
    }

    private void addHistoryEntry(String entry) {
        Label.LabelStyle labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font.getData().setScale(0.8f);

        Label historyLabel = new Label(entry, labelStyle);
        TextButton editButton = new TextButton("Edit", skin);
        TextButton deleteButton = new TextButton("Delete", skin);

        float buttonWidth = Gdx.graphics.getWidth() / 5f;
        float buttonHeight = Gdx.graphics.getHeight() / 20f;
        editButton.setSize(buttonWidth, buttonHeight);
        deleteButton.setSize(buttonWidth, buttonHeight);

        historyLabel.setWrap(true);
        historyLabel.setWidth(Gdx.graphics.getWidth() / 2f);

        table.add(historyLabel).width(Gdx.graphics.getWidth() * 0.6f).pad(5);
        table.add(editButton).pad(5);
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