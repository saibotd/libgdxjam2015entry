package com.gdxjam.magellan;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxjam.magellan.drones.DroneRoutine;
import com.gdxjam.magellan.screen.MapScreen;
import com.gdxjam.magellan.screen.StoryScreen;
import com.gdxjam.magellan.screen.TitleScreen;
import com.gdxjam.magellan.screen.WindowScreen;
import com.gdxjam.magellan.tweening.ActorAccessor;
import com.gdxjam.magellan.tweening.SpriteAccessor;

public class MagellanGame extends Game{
    public static AssetManager assets;
    public Universe universe;
    public MapScreen mapScreen;
    public WindowScreen windowScreen;
    public StoryScreen storyScreen;
    public static GameState gameState;
    public static boolean DEBUG = false;
    public static MagellanGame instance;
    public static SoundFx soundFx;
    public TitleScreen titleScreen;

    public MagellanGame(){
        instance = this;
        gameState = new GameState(this);
		universe = new Universe(this);
	}

	public void create() {

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Actor.class, new ActorAccessor());
        assets = new AssetManager();
        assets.load("pixel.png", Texture.class);
        assets.load("dot.png", Texture.class);
        assets.load("circle.png", Texture.class);
        assets.load("title.png", Texture.class);
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
        assets.load("enemy_fighter.png", Texture.class);
        assets.load("enemy_transport.png", Texture.class);
        assets.load("enemy_drone.png", Texture.class);
        assets.load("pirateship.png", Texture.class);
        assets.load("shop.png", Texture.class);
        assets.load("sectorview_asteroids.png", Texture.class);
        assets.load("sectorview_asteroids_resources.png", Texture.class);
        assets.load("sectorview_planet_1.png", Texture.class);
        assets.load("sectorview_planet_2.png", Texture.class);
        assets.load("sectorview_planet_3.png", Texture.class);
        assets.load("sectorview_planet_4.png", Texture.class);
        assets.load("sectorview_ship.png", Texture.class);
        assets.load("sectorview_ship_shield.png", Texture.class);
        assets.load("sectorview_drone.png", Texture.class);
        assets.load("sectorview_drone_gun.png", Texture.class);
        assets.load("sectorview_drone_mine.png", Texture.class);
        assets.load("sectorview_drone_thruster.png", Texture.class);
        assets.load("sectorview_enemy_fighter.png", Texture.class);
        assets.load("sectorview_enemy_transporter.png", Texture.class);
        assets.load("bar.png", Texture.class);
        assets.load("sectorview_enemy_fighter_shield.png", Texture.class);
        assets.load("sectorview_enemy_transporter_shield.png", Texture.class);
        assets.load("sectorview_enemy_drone.png", Texture.class);
        assets.load("sectorview_pirates.png", Texture.class);
        assets.load("sectorview_shop.png", Texture.class);
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
        assets.load("sounds/shield_1.mp3", Sound.class);
        assets.load("sounds/shield_2.mp3", Sound.class);
        assets.load("sounds/nope.mp3", Sound.class);
        assets.load("sounds/doomed.wav", Sound.class);
        assets.load("sounds/explosion1.wav", Sound.class);
        assets.load("sounds/explosion2.wav", Sound.class);
        assets.load("sounds/explosion3.wav", Sound.class);
        assets.load("sounds/explosion4.wav", Sound.class);
        assets.load("sounds/weaponfire2.wav", Sound.class);
        assets.load("sounds/weaponfire3.wav", Sound.class);
        assets.load("sounds/weaponfire5.wav", Sound.class);
        assets.load("sounds/weaponfire6.wav", Sound.class);
        assets.finishLoading();
        soundFx = new SoundFx();
        titleScreen = new TitleScreen(this);
        mapScreen = new MapScreen(this);
        windowScreen = new WindowScreen(this);
        storyScreen = new StoryScreen(this);
        setScreen(titleScreen);
        Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);

	}

    public void showWindowScreen() {
        setScreen(windowScreen);
    }

    public void showMapScreen() {
        setScreen(mapScreen);
    }
    public void showStoryScreen() {
        setScreen(storyScreen);
    }

    public void showTitleScreen() {
        setScreen(titleScreen);
    }

    public void restartGame() {
        mapScreen.dispose();
        windowScreen.dispose();
        gameState = new GameState(this);
        universe = new Universe(this);
        mapScreen = new MapScreen(this);
        windowScreen = new WindowScreen(this);
    }
}
