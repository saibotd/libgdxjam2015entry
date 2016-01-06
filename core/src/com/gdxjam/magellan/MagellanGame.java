package com.gdxjam.magellan;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxjam.magellan.drones.DroneRoutine;
import com.gdxjam.magellan.screen.MapScreen;
import com.gdxjam.magellan.screen.WindowScreen;
import com.gdxjam.magellan.tweening.ActorAccessor;
import com.gdxjam.magellan.tweening.SpriteAccessor;

public class MagellanGame extends Game{
    public static AssetManager assets;
    public Universe universe;
    public MapScreen mapScreen;
    public WindowScreen windowScreen;
    public static GameState gameState;
    public static boolean DEBUG = false;
    public static MagellanGame instance;
    public static SoundFx soundFx;

    public MagellanGame(){
        instance = this;
        gameState = new GameState(this);
        gameState.UNLOCKED_ROUTINES.add(DroneRoutine.ROUTINES.MINING);
        gameState.UNLOCKED_ROUTINES.add(DroneRoutine.ROUTINES.SCOUTING);
		universe = new Universe(this);
	}

	public void create() {

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        assets = new AssetManager();
        assets.load("pixel.png", Texture.class);
        assets.load("dot.png", Texture.class);
        assets.load("circle.png", Texture.class);
        assets.load("map_playership.png", Texture.class);
        assets.load("map_meteoroids_emptysector.png", Texture.class);
        assets.load("map_meteoroids_planetsector.png", Texture.class);
        assets.load("map_planet_1.png", Texture.class);
        assets.load("map_planet_2.png", Texture.class);
        assets.load("map_planet_3.png", Texture.class);
        assets.load("map_planet_4.png", Texture.class);
        assets.load("map_planet_claimed.png", Texture.class);
        assets.load("map_sector.png", Texture.class);
        assets.load("map_sector_notvisited.png", Texture.class);
        assets.load("map_shop.png", Texture.class);
        assets.load("drone.png", Texture.class);
        assets.load("drone_gun.png", Texture.class);
        assets.load("drone_mine.png", Texture.class);
        assets.load("drone_thruster.png", Texture.class);
        assets.load("sectorview_asteroids.png", Texture.class);
        assets.load("sectorview_asteroids_resources.png", Texture.class);
        assets.load("sectorview_planet_1.png", Texture.class);
        assets.load("sectorview_planet_2.png", Texture.class);
        assets.load("sectorview_planet_3.png", Texture.class);
        assets.load("sectorview_planet_4.png", Texture.class);
        assets.load("sectorview_ship.png", Texture.class);
        assets.load("bg.png", Texture.class);
        assets.load("topbarBg.png", Texture.class);
        assets.load("skin/uiskin.json", Skin.class);
        assets.load("bgm0.mp3", Music.class);
        assets.load("bgm1.mp3", Music.class);
        assets.load("bgm2.mp3", Music.class);
        assets.load("bgm3.mp3", Music.class);
        assets.load("battle.mp3", Music.class);
        assets.load("sounds/buy_sell.mp3", Sound.class);
        assets.load("sounds/ship_jump_2.mp3", Sound.class);
        assets.load("sounds/button.mp3", Sound.class);
        assets.load("sounds/mine.mp3", Sound.class);
        assets.load("sounds/population.mp3", Sound.class);
        assets.load("sounds/upgrade.mp3", Sound.class);
        assets.load("sounds/nope.mp3", Sound.class);
        assets.finishLoading();
        soundFx = new SoundFx();
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
