package com.oopfinal.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oopfinal.game.managers.MusicManager;
import com.oopfinal.game.screens.LoadingScreen;
import com.oopfinal.game.screens.MainMenuScreen;
import com.oopfinal.game.screens.SplashScreen;

public class OOPFinal extends Game {
	public SpriteBatch batch;
	public  static  final float PPM=100;
	public static int V_WIDTH=1856;
	public  static int V_HEIGHT=1088;
	private MusicManager musicManager;
	private Screen mainMenuScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		this.setScreen(new SplashScreen(this));


//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}

	public MusicManager getMusicManager() {
		return null;
	}
}
