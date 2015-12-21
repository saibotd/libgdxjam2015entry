package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class GameObj {
    public Sector sector;
    enum Factions{NEUTRAL, PLAYER, ENEMY, PIRATE};
    public Factions faction = Factions.NEUTRAL;
    public GameObj(Sector sector){
        this.sector = sector;
        sector.gameObjs.add(this);
    }
    public void tick(){

    }
}
