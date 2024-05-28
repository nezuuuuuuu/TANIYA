package com.oopfinal.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.oopfinal.game.OOPFinal;
import com.oopfinal.game.crud.SQLMethods;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("OOPFinal");

		//Create tables
//		SQLMethods.createGame();

		// Set window dimensions
		int windowWidth = 793;
		int windowHeight = 544;
		config.setWindowedMode(windowWidth, windowHeight);
		config.setResizable(true);

		// Get the primary monitor's resolution
		Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();

		// Calculate the window's position to center it
		int positionX = (displayMode.width - windowWidth) / 2;
		int positionY = (displayMode.height - windowHeight) / 2;
		config.setWindowPosition(positionX, positionY);

		// Start the application
		new Lwjgl3Application(new OOPFinal(), config);
	}
}
