package com.gdxjam.magellan;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.screen.MapScreen;

import java.io.IOException;

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
        assets.finishLoading();
        setScreen(new MapScreen(this));
	}
}
