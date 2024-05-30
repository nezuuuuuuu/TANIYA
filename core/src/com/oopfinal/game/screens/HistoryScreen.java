package com.oopfinal.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.oopfinal.game.OOPFinal;

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

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        history = new String[]{
                "Player 1: Alice vs Player 2: Bob",
                "Player 1: Carol vs Player 2: Dave",
                "Player 1: Eve vs Player 2: Frank"
        };

        for (String entry : history) {
            addHistoryEntry(entry);
        }
    }

    private void addHistoryEntry(String entry) {
        Label historyLabel = new Label(entry, skin);
        TextButton editButton = new TextButton("Edit", skin);
        TextButton deleteButton = new TextButton("Delete", skin);

        // Set button sizes to be proportional to screen size
        float buttonWidth = Gdx.graphics.getWidth() / 5f;
        float buttonHeight = Gdx.graphics.getHeight() / 20f;
        editButton.setSize(buttonWidth, buttonHeight);
        deleteButton.setSize(buttonWidth, buttonHeight);

        // Set label width to adapt to screen width
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
