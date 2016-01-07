package com.gdxjam.magellan.gameobj;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.tweening.SpriteAccessor;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MovingGameObj extends GameObj {

    public Sector lastSector;
    public TweenManager tweenManager;
    public Sprite spriteVessel;
    public int sectorSlot;
    public Vector2 parkingPosition;
    public Vector2 lastParkingPosition;
    public float parkingAngle;

    public MovingGameObj(Sector sector) {
        super(sector);
        this.tweenManager = new TweenManager();
        sectorSlot = -1;
        parkingPosition = new Vector2();
        parkingAngle = 0;
    }

    public void moveTo(Sector sector) {
        lastSector = this.sector;
        lastParkingPosition = parkingPosition.cpy();
        this.sector.gameObjs.removeValue(this, true);
        this.sector = sector;
        sector.gameObjs.add(this);

        getFreeSectorSlot();
        getParkingPosition();

        float flightAngle = (float)Math.atan2(parkingPosition.y - lastParkingPosition.y, parkingPosition.x - lastParkingPosition.x)*180f/(float)Math.PI-90f;
        while (flightAngle < -180) flightAngle += 360;
        while (flightAngle > 180) flightAngle -= 360;

        tweenManager.killAll();
        Timeline.createSequence()
                .push(Tween.to(this.spriteVessel, SpriteAccessor.ROTATION, 0.3f).target(flightAngle))
                .push(Tween.to(this.spriteVessel, SpriteAccessor.POSITION_XY, 0.5f).target(parkingPosition.x, parkingPosition.y).ease(TweenEquations.easeInOutQuint))
                .push(Tween.to(this.spriteVessel, SpriteAccessor.ROTATION, 1f).target(parkingAngle).ease(TweenEquations.easeInOutCubic))
                .start(tweenManager);
    }

    public void render(float deltaTime) {
        super.render(deltaTime);
        tweenManager.update(deltaTime);
    }

    public void getFreeSectorSlot() {
        slots:
        for(int i=0; i<sector.gameObjs.size; i++) {
            for (GameObj gameObj : sector.gameObjs) {
                if (gameObj instanceof MovingGameObj) {
                    if (((MovingGameObj) gameObj).sectorSlot == i && (gameObj.toString() != this.toString())) {
                        continue slots;
                    }
                }
            }
            sectorSlot = i;
            return;
        }
        sectorSlot = sector.gameObjs.size;
    }

    public void getParkingPosition() {
        int shipsPerRow = 8;
        float angle = 360 / shipsPerRow * sectorSlot;
        float row = MathUtils.floor(sectorSlot/shipsPerRow);
        if (row % 2 == 0) angle += (360 / shipsPerRow) / 2;
        float distance = 30 + row * 15;

        while (angle < -180) angle += 360;
        while (angle > 180) angle -= 360;


        float dx = distance * MathUtils.cosDeg(angle);
        float dy = distance * MathUtils.sinDeg(angle);

        parkingPosition = sector.position.cpy().sub(spriteVessel.getWidth()/2, spriteVessel.getHeight()/2).add(dx, dy);
        parkingAngle = (angle + 90) % 360;
    }

}
