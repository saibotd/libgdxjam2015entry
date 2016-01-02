package com.gdxjam.magellan.gameobj;

import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class GameObj {

    public Sector sector;
    public enum Factions{NEUTRAL, PLAYER, ENEMY, PIRATE};
    public Factions faction = Factions.NEUTRAL;
    public String submenuOpen = "";

    public GameObj(Sector sector){
        this.sector = sector;
        sector.gameObjs.add(this);
    }
    public void tick(){

    }
    public void render(float deltaTime) {

    }

    public void closeWindow() {
        MagellanGame.instance.windowScreen.closeWindow();
    }

    public void showInteractionWindow() {
        closeWindow();
        MagellanGame.instance.windowScreen.showInteractionWindow((IDrawableWindow) this);
    }
}
