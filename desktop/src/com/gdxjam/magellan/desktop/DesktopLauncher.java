package com.gdxjam.magellan.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdxjam.magellan.MagellanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
        config.backgroundFPS = 60;
        config.foregroundFPS = 60;
        config.samples = 16;
		config.vSyncEnabled = true;
        config.resizable = false;
		new LwjglApplication(new MagellanGame(), config);
	}
}
