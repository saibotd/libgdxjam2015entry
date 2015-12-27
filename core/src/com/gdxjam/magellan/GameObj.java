package com.gdxjam.magellan;

import aurelienribon.tweenengine.TweenManager;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class GameObj {

    public Sector sector;
    public enum Factions{NEUTRAL, PLAYER, ENEMY, PIRATE};
    public Factions faction = Factions.NEUTRAL;
    public TweenManager tweenManager;

    public GameObj(Sector sector){
        this.sector = sector;
        sector.gameObjs.add(this);
        this.tweenManager = new TweenManager();
    }
    public void tick(){

    }
    public void render(float deltaTime) {
        tweenManager.update(deltaTime);
    }
}
