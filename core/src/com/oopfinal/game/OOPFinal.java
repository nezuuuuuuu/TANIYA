package com.oopfinal.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oopfinal.game.screens.SplashScreen;

public class OOPFinal extends Game {
	public SpriteBatch batch;
	public  static  final float PPM=100;
	public static int V_WIDTH=2000;
	public  static int V_HEIGHT=2000;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		setScreen(new LogInScreen(this));
		this.setScreen(new SplashScreen(this));

		//full screen
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
}
