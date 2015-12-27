package com.gdxjam.magellan.ships;

import com.badlogic.gdx.graphics.Color;
import com.gdxjam.magellan.*;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShipSettler extends AiShip {

    public AiShipSettler(Sector sector) {
        super(sector);
        faction = Factions.ENEMY;
    }

    private void decideState(){
        for (int i = 0; i < sector.gameObjs.size; i++){
            GameObj gameObj = sector.gameObjs.get(i);
            if(gameObj instanceof IDestroyable && gameObj.faction != faction){
                if(Math.random() < .5){
                    target = (IDestroyable) gameObj;
                }
            }
            if(gameObj instanceof Planet && gameObj.faction == Factions.NEUTRAL){
                Planet planet = (Planet) gameObj;
                planet.claim(this);
                planet.populate(this, 500);
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

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();
        spriteDot.setColor(Color.ORANGE);
    }

}
