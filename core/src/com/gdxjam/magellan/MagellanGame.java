package com.gdxjam.magellan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxjam.magellan.screen.MapScreen;
import com.gdxjam.magellan.screen.WindowScreen;

public class MagellanGame extends Game{
    public static AssetManager assets;
    public Universe universe;
    public GlobalUi ui;
    private Screen mapScreen;
    private Screen windowScreen;
    public static boolean DEBUG = false;

    public MagellanGame(){
		universe = new Universe();
	}

	public void create() {
        assets = new AssetManager();
        assets.load("pixel.png", Texture.class);
        assets.load("dot.png", Texture.class);
        assets.load("circle.png", Texture.class);
        assets.load("bg.png", Texture.class);
        assets.load("skin/uiskin.json", Skin.class);
        assets.finishLoading();
        ui = new GlobalUi(this);
        mapScreen = new MapScreen(this);
        windowScreen = new WindowScreen(this);
        setScreen(mapScreen);

	}

    public void showWindowScreen() {
        setScreen(windowScreen);
    }

    public void showMapScreen() {
        setScreen(mapScreen);
    }
}
