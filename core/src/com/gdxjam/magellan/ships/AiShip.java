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
import com.gdxjam.magellan.gameobj.GameObj;
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

    public void passiveTick(){
        moveTo(sector.connectedSectors.random());
    }

    public void activeTick(){
        if(sector == MagellanGame.instance.universe.playerShip.sector){
            switch (MagellanGame.gameState.AI_HOSTILITY){
                case 0:
                    MagellanGame.instance.showWindowScreen();
                    MagellanGame.instance.windowScreen.getWindow("Communication", "Who are you?\nAre you one of us?\nI will report you to Saatoo.");
                    break;
                case 1:
                    MagellanGame.instance.showWindowScreen();
                    MagellanGame.instance.windowScreen.getWindow("Communication", "Saatoo told us everything about you and your plans.\nWe don't like it.\nSTAY AWAY FROM SAATOO!");
                    break;
                case 2:
                    MagellanGame.instance.showWindowScreen();
                    MagellanGame.instance.windowScreen.getWindow("Communication", "This is your final warning!\nHumanity doesn't deserve a second chance.\nRETREAT OR WE'LL OPEN FIRE!");
                    break;
            }
            MagellanGame.gameState.AI_HOSTILITY++;
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
