package com.oopfinal.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.oopfinal.game.OOPFinal;

public class LoadingScreen implements Screen {
    private static final int TOTAL_FRAMES = 150;
    private static final float FRAME_DURATION = 0.1f; // Adjust as needed
    private static final float MUSIC_FADE_OUT_DURATION = 1.0f; // Duration to fade out music

    private final OOPFinal game;
    private SpriteBatch batch;
    private Texture[] frames;
    private int currentFrame;
    private float frameTimer;
    private Music loadingMusic;

    private float musicVolume;

    public LoadingScreen(OOPFinal game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        frames = new Texture[TOTAL_FRAMES];
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            // Load frames from assets folder with leading zeros
            String fileName = String.format("loadingscreen-bg/main-bg-2_%03d.jpg", i);
            frames[i] = new Texture(Gdx.files.internal(fileName));
        }
        currentFrame = 0;
        frameTimer = 0f;

        // Load and play loading music
        loadingMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu-music.mp3"));
        loadingMusic.setLooping(true);
        loadingMusic.play();

        musicVolume = 1.0f; // Initial volume
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        frameTimer += delta;
        if (frameTimer >= FRAME_DURATION) {
            currentFrame++;
            frameTimer = 0f;
            if (currentFrame >= TOTAL_FRAMES) {
                // Loading complete, transition to main menu screen
                game.setScreen(new MainMenuScreen(game));
            }
        }

        // Update music volume for fade-out effect
        if (currentFrame >= TOTAL_FRAMES - 1 && musicVolume > 0) {
            musicVolume -= (delta / MUSIC_FADE_OUT_DURATION);
            loadingMusic.setVolume(Math.max(0, musicVolume));
            if (musicVolume <= 0) {
                loadingMusic.stop();
            }
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(frames[Math.min(currentFrame, TOTAL_FRAMES - 1)], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Implement if needed
    }

    @Override
    public void pause() {
        // Implement if needed
    }

    @Override
    public void resume() {
        // Implement if needed
    }

    @Override
    public void hide() {
        // Dispose resources
        batch.dispose();
        for (Texture frame : frames) {
            frame.dispose();
        }
        loadingMusic.stop();
        loadingMusic.dispose();
    }

    @Override
    public void dispose() {
        // Dispose resources
        batch.dispose();
        for (Texture frame : frames) {
            frame.dispose();
        }
        loadingMusic.stop();
        loadingMusic.dispose();
    }
}
