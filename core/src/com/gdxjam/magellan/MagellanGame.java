package com.gdxjam.magellan;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxjam.magellan.screen.MapScreen;
import com.gdxjam.magellan.screen.WindowScreen;

public class MagellanGame extends Game{
    public static AssetManager assets;
    public Universe universe;
    public GlobalUi ui;
    private Screen mapScreen;
    private Screen windowScreen;
    public GameState gameState;
    public static boolean DEBUG = false;

    public MagellanGame(){
		universe = new Universe(this);
	}

	public void create() {

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        gameState = new GameState(this);
        assets = new AssetManager();
        assets.load("pixel.png", Texture.class);
        assets.load("dot.png", Texture.class);
        assets.load("circle.png", Texture.class);
        assets.load("map_playership.png", Texture.class);
        assets.load("drone_default.png", Texture.class);
        assets.load("bg.png", Texture.class);
        assets.load("topbarBg.png", Texture.class);
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
