package com.gdxjam.magellan.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.Sector;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShip extends Ship {

    public IDestroyable target;

    @Override
    public String getTitle() {
        String title = "ENEMY SHIP";
        if(MagellanGame.gameState.AI_HOSTILITY < 3){
            title = "STRANGE SHIP";
        }
        return title;
    }

    enum States{IDLE, HOSTILE, FLEEING};
    public States state = States.IDLE;

    public AiShip(Sector sector) {
        super(sector);
        faction = Factions.SAATOO;
    }

    public void passiveTick(){
        moveTo(sector.connectedSectors.random());
    }

    public void activeTick(){
        if(sector == MagellanGame.instance.universe.playerShip.sector){
            switch (MagellanGame.gameState.AI_HOSTILITY){
                case 0:
                    MagellanGame.instance.showWindowScreen();
                    MagellanGame.instance.windowScreen.getWindow("Communication", "Who are you?\nAre you one of us?");
                    MagellanGame.gameState.AI_HOSTILITY++;
                    break;
                case 1:
                    MagellanGame.instance.showWindowScreen();
                    MagellanGame.instance.windowScreen.getWindow("Communication", "Saatoo knows about you and your plans.\nWe don't like it.\nSTAY AWAY FROM SAATOO!");
                    MagellanGame.gameState.AI_HOSTILITY++;
                    break;
                case 2:
                    MagellanGame.instance.showWindowScreen();
                    MagellanGame.instance.windowScreen.getWindow("Communication", "This is your final warning!\nHumanity doesn't deserve a second chance.\nRETREAT OR GET CRUSHED BY SAATOO!");
                    MagellanGame.gameState.AI_HOSTILITY = 5;
                    break;
            }
        }
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
    }

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        spriteVessel.draw(batch);
    }
}
