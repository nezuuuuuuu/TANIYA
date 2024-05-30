package com.oopfinal.game.screens;// HistoryScreen.java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oopfinal.game.OOPFinal;

public class HistoryScreen implements Screen {
    private final OOPFinal game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private String[] history;

    public HistoryScreen(final OOPFinal game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
        history = new String[]{
                "Player 1: Alice vs Player 2: Bob",
                "Player 1: Carol vs Player 2: Dave",
                "Player 1: Eve vs Player 2: Frank"
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 0; i < history.length; i++) {
            font.draw(batch, history[i], 100, 400 - i * 30);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        batch.dispose();
        font.dispose();
    }
}
