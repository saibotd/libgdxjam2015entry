package com.gdxjam.magellan;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MovingGameObj extends GameObj {

    public Sector lastSector;

    public MovingGameObj(Sector sector) {
        super(sector);
    }

    public void moveTo(Sector sector) {
        lastSector = this.sector;
        this.sector.gameObjs.removeValue(this, true);
        this.sector = sector;
        sector.gameObjs.add(this);
    }

    public void render(float deltaTime) {
        super.render(deltaTime);
    }
}
