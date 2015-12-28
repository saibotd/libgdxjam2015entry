package com.gdxjam.magellan.ships;

import com.badlogic.gdx.graphics.Color;
import com.gdxjam.magellan.IDestroyable;
import com.gdxjam.magellan.Sector;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShip extends Ship {

    public IDestroyable target;

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    enum States{IDLE, HOSTILE, FLEEING};
    public States state = States.IDLE;

    public AiShip(Sector sector) {
        super(sector);
        faction = Factions.ENEMY;
    }

    public void tick(){
        moveTo(sector.connectedSectors.random());
    }

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();
        spriteDot.setColor(Color.RED);
    }
}
