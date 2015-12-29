package com.gdxjam.magellan;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class MovingGameObj extends GameObj {

    public Sector lastSector;
    public TweenManager tweenManager;
    public Sprite spriteVessel;

    public MovingGameObj(Sector sector) {
        super(sector);
        this.tweenManager = new TweenManager();
    }

    public void moveTo(Sector sector) {
        lastSector = this.sector;
        this.sector.gameObjs.removeValue(this, true);
        this.sector = sector;
        sector.gameObjs.add(this);
    }

    public void render(float deltaTime) {
        super.render(deltaTime);
        tweenManager.update(deltaTime);
    }
}
