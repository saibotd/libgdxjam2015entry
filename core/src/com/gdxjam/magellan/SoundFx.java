package com.gdxjam.magellan;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Felix on 02.01.2016.
 */
public class SoundFx {

    public Sound shipJump;
    public Sound buy;
    public Sound button;
    public Sound mine;
    public Sound population;
    public Sound upgrade;
    public Sound nope;
    public Sound doomed;
    public Array<Sound> explosions = new Array<Sound>();
    public Array<Sound> weaponFire = new Array<Sound>();
    public Array<Sound> weaponFireSmall = new Array<Sound>();
    public Array<Sound> shield = new Array<Sound>();

    public SoundFx() {
        shipJump = MagellanGame.assets.get("sounds/ship_jump_2.mp3", Sound.class);
        buy = MagellanGame.assets.get("sounds/buy_sell.mp3", Sound.class);
        button = MagellanGame.assets.get("sounds/button.mp3", Sound.class);
        mine = MagellanGame.assets.get("sounds/mine.mp3", Sound.class);
        population = MagellanGame.assets.get("sounds/population.mp3", Sound.class);
        upgrade = MagellanGame.assets.get("sounds/upgrade.mp3", Sound.class);
        nope = MagellanGame.assets.get("sounds/nope.mp3", Sound.class);
        doomed = MagellanGame.assets.get("sounds/doomed.wav", Sound.class);
        explosions.add(MagellanGame.assets.get("sounds/explosion1.wav", Sound.class));
        explosions.add(MagellanGame.assets.get("sounds/explosion2.wav", Sound.class));
        explosions.add(MagellanGame.assets.get("sounds/explosion3.wav", Sound.class));
        explosions.add(MagellanGame.assets.get("sounds/explosion4.wav", Sound.class));
        weaponFire.add(MagellanGame.assets.get("sounds/weaponfire2.wav", Sound.class));
        weaponFire.add(MagellanGame.assets.get("sounds/weaponfire3.wav", Sound.class));
        weaponFireSmall.add(MagellanGame.assets.get("sounds/weaponfire5.wav", Sound.class));
        weaponFireSmall.add(MagellanGame.assets.get("sounds/weaponfire6.wav", Sound.class));
        shield.add(MagellanGame.assets.get("sounds/shield_1.mp3", Sound.class));
        shield.add(MagellanGame.assets.get("sounds/shield_2.mp3", Sound.class));

    }


}
