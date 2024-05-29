package com.oopfinal.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class MusicManager implements Disposable {
    private Music backgroundMusic;

    public MusicManager() {
        // Load your music file
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu-music.mp3"));
        backgroundMusic.setLooping(true); // Make the music loop
    }

    public void play() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stop() {
        backgroundMusic.stop();
    }

    public void setVolume(float volume) {
        backgroundMusic.setVolume(volume);
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
    }
}
