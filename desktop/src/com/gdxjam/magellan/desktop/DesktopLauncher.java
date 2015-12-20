package com.gdxjam.magellan.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdxjam.magellan.MagellanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
        config.backgroundFPS = 60;
        config.foregroundFPS = 60;
        config.samples = 8;
        config.resizable = true;
		new LwjglApplication(new MagellanGame(), config);
	}
}
