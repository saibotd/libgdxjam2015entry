package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShipSettler extends AiShip {

    public AiShipSettler(Sector sector) {
        super(sector);
        faction = Factions.ENEMY;
        colorOnMap = Color.ORANGE;
        sizeOnMap = 15;
    }

    private void decideState(){
        for (GameObj gameObj : sector.gameObjs){
            if(gameObj instanceof IDestroyable && gameObj.faction != faction){
                if(Math.random() < .5){
                    target = (IDestroyable) gameObj;
                }
            }
        }
        if(target != null && target.isAlive()){
            state = States.FLEEING;
            if(Math.random() < .2){
                state = States.HOSTILE;
            }
            return;
        }
        target = null;
        state = States.IDLE;
    }

    public void tick(){
        decideState();
        switch (state){
            case IDLE:
                if(Math.random() < .5) super.tick();
                break;
            case HOSTILE:
                target.receiveDamage(attack);
                break;
            case FLEEING:
                super.tick();
                break;
        }
    }
}
