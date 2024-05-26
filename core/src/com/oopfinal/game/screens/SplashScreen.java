package com.oopfinal.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oopfinal.game.OOPFinal;


public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Texture splashImg;
    private Game game;
    private float timeSinceShown;
    private Viewport viewport;
    private float alpha; // Transparency value for fade-in and fade-out

    public SplashScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        splashImg = new Texture("maps/images/SplashScreen.png");
        timeSinceShown = 0;  // Initialize the timer
        alpha = 0f;

        viewport = new ScreenViewport();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        System.out.println(Gdx.graphics.getWidth()+"   "+Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timeSinceShown += delta;

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();


        float x = (viewport.getWorldWidth() - splashImg.getWidth()) / 2;
        float y = (viewport.getWorldHeight() - splashImg.getHeight()) / 2;


        batch.setColor(1, 1, 1, alpha);
        batch.draw(splashImg, x, y);
        batch.setColor(1, 1, 1, 1);

        batch.end();


        if (alpha < 1 && timeSinceShown <= 3) {
            alpha += delta; // Fade-in
        } else if (alpha > 0) {
            alpha = Math.max(0, alpha - delta); // Fade-out
        }


        if (timeSinceShown > 4) {
            game.setScreen((Screen) new GameScreen((OOPFinal) game)); // Change to your main menu or game screen
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        batch.dispose();
        splashImg.dispose();
    }
}

