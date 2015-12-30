package com.gdxjam.magellan;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxjam.magellan.screen.BaseScreen;
import com.gdxjam.magellan.screen.MapScreen;
import com.gdxjam.magellan.screen.WindowScreen;

public class MagellanGame extends Game{
    public static AssetManager assets;
    public Universe universe;
    public MapScreen mapScreen;
    public WindowScreen windowScreen;
    public static GameState gameState;
    public static boolean DEBUG = false;
    public static MagellanGame instance;

    public MagellanGame(){
        instance = this;
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
        assets.load("map_meteoroids_emptysector.png", Texture.class);
        assets.load("map_meteoroids_planetsector.png", Texture.class);
        assets.load("map_planet_1.png", Texture.class);
        assets.load("map_planet_2.png", Texture.class);
        assets.load("map_planet_claimed.png", Texture.class);
        assets.load("map_sector.png", Texture.class);
        assets.load("map_sector_notvisited.png", Texture.class);
        assets.load("map_shop.png", Texture.class);
        assets.load("drone.png", Texture.class);
        assets.load("bg.png", Texture.class);
        assets.load("topbarBg.png", Texture.class);
        assets.load("skin/uiskin.json", Skin.class);
        assets.load("bgm0.mp3", Music.class);
        assets.load("bgm1.mp3", Music.class);
        assets.load("bgm2.mp3", Music.class);
        assets.load("bgm3.mp3", Music.class);
        assets.load("battle.mp3", Music.class);
        assets.finishLoading();
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
