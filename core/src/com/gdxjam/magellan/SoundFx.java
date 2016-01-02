package com.gdxjam.magellan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Felix on 02.01.2016.
 */
public class SoundFx {

    public Sound ship_jump;
    public Sound buy_sell;
    public Sound button;
    public Sound mine;
    public Sound population;
    public Sound upgrade;

    public SoundFx() {
        ship_jump = MagellanGame.assets.get("sounds/ship_jump_2.mp3", Sound.class);
        buy_sell = MagellanGame.assets.get("sounds/buy_sell.mp3", Sound.class);
        button = MagellanGame.assets.get("sounds/button.mp3", Sound.class);
        mine = MagellanGame.assets.get("sounds/mine.mp3", Sound.class);
        population = MagellanGame.assets.get("sounds/population.mp3", Sound.class);
        upgrade = MagellanGame.assets.get("sounds/upgrade.mp3", Sound.class);

    }


}
