package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

    public PlayerShip(Sector sector) {
        super(sector);
        sector.discovered = true;
        colorOnMap = Color.YELLOW;
        sizeOnMap = 15;
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        sector.discovered = true;
    }
}
