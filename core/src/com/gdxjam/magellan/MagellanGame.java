package com.gdxjam.magellan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxjam.magellan.screen.MapScreen;

public class MagellanGame extends Game{
    public static AssetManager assets;
    public Universe universe;

	public MagellanGame(){
		universe = new Universe();
	}

	public void create() {
        assets = new AssetManager();
        assets.load("pixel.png", Texture.class);
        assets.load("dot.png", Texture.class);
        assets.load("circle.png", Texture.class);
        assets.load("skin/uiskin.json", Skin.class);
        assets.finishLoading();
        setScreen(new MapScreen(this));
	}
}
