package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class GameObj {
    public Sector sector;
    public Color colorOnMap = Color.CYAN;
    public int sizeOnMap = 10;
    enum Factions{NEUTRAL, PLAYER, ENEMY, PIRATE};
    public Factions faction = Factions.NEUTRAL;
    public GameObj(Sector sector){
        this.sector = sector;
        sector.gameObjs.add(this);
    }
    public void tick(){

    }
}
