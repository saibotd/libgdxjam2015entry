package com.gdxjam.magellan;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class GameObj {
    public Sector sector;
    enum Factions{NEUTRAL, PLAYER, ENEMY, PIRATE};
    public Factions faction = Factions.NEUTRAL;
    private MagellanGame game;
    public GameObj(Sector sector, MagellanGame game){
        this.sector = sector;
        this.game = game;
        sector.gameObjs.add(this);
    }
    public void tick(){

    }
}
