package com.gdxjam.magellan.gameobj;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.ships.PlayerShip;
import sun.rmi.runtime.Log;

import java.util.Comparator;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MovingGameObj extends GameObj {

    public Sector lastSector;
    public TweenManager tweenManager;
    public Sprite spriteVessel;
    public int sectorSlot;
    public Vector2 parkingPosition;
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
        this.sector.gameObjs.removeValue(this, true);
        this.sector = sector;
        sector.gameObjs.add(this);
        getFreeSectorSlot();
        getParkingPosition();
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
                    if (((MovingGameObj) gameObj).sectorSlot == i && ((MovingGameObj) gameObj != this)) {
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
        float angle = 360 / 12 * (sectorSlot+1);
        float distance = 30 + MathUtils.floor((sectorSlot + 1) / 12) * 15;
        System.out.println(distance);

        float dx = distance * MathUtils.cosDeg(angle);
        float dy = distance * MathUtils.sinDeg(angle);

        parkingPosition = sector.position.cpy().sub(spriteVessel.getWidth()/2, spriteVessel.getHeight()/2).add(dx, dy);
        parkingAngle = angle + 90;
    }

}
