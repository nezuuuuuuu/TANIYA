package com.oopfinal.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oopfinal.game.screens.LogInScreen;

public class OOPFinal extends Game {
	public SpriteBatch batch;
	public  static  final float PPM=100;
	public static int V_WIDTH=1856;
	public  static int V_HEIGHT=1120;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new LogInScreen(this));

//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	}

	@Override
	public void render () {
	super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
