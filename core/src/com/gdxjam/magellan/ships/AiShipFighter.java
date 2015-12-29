package com.gdxjam.magellan.ships;

import com.gdxjam.magellan.Battle;
import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.IDestroyable;
import com.gdxjam.magellan.Sector;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShipFighter extends AiShip {

    public AiShipFighter(Sector sector) {
        super(sector);
        faction = Factions.ENEMY;
    }

    private void decideState(){
        for (GameObj gameObj : sector.gameObjs){
            if(gameObj instanceof IDestroyable && gameObj.faction == Factions.PLAYER){
                if(Math.random() < .5){
                    target = (IDestroyable) gameObj;
                }
            }
        }
        if(target != null && target.isAlive()){
            state = States.HOSTILE;
            if(health < 20 && Math.random() < .5){
                state = States.FLEEING;
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
                new Battle(this, target);
                break;
            case FLEEING:
                super.tick();
                break;
        }
    }
}
