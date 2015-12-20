package com.gdxjam.magellan;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MovingGameObj extends GameObj {
    public MovingGameObj(Sector sector) {
        super(sector);
    }

    public void moveTo(Sector sector) {
        this.sector.gameObjs.removeValue(this, true);
        this.sector = sector;
        sector.gameObjs.add(this);
    }
}
