package com.gdxjam.magellan.ships;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxjam.magellan.MagellanColors;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.tweening.SpriteAccessor;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShip extends Ship {

    public IDestroyable target;

    @Override
    public String getTitle() {
        return "ENEMY SHIP";
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

    public void moveTo(Sector sector) {
        super.moveTo(sector);
    }

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();
        //spriteDot.setColor(Color.RED);
        spriteVessel = new Sprite(MagellanGame.assets.get("map_playership.png", Texture.class));
        spriteVessel.setSize(20, 20);
        spriteVessel.setOriginCenter();

        sectorSlot = 0;
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);
        spriteVessel.setColor(MagellanColors.FACTION_ENEMY);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        spriteVessel.draw(batch);
    }
}
