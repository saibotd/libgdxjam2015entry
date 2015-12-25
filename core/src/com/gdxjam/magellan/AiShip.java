package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShip extends Ship {

    public IDestroyable target;

    enum States{IDLE, HOSTILE, FLEEING};
    public States state = States.IDLE;

    public AiShip(Sector sector, MagellanGame game) {
        super(sector, game);
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
