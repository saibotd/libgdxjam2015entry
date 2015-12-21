package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShip extends Ship {

    public IDestroyable target;

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
        sprite.setColor(Color.RED);
    }
}
