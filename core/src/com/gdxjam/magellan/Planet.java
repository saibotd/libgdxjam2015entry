package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Planet extends GameObj {
    public Planet(Sector sector) {
        super(sector);
        sizeOnMap = 20;
        colorOnMap = Color.MAGENTA;
    }
}
